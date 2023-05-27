@file:Suppress("DEPRECATION")

package io.sn.dumortierite.abstracts

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.ItemState
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.AdvancedMenuClickHandler
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker
import me.mrCookieSlime.Slimefun.api.BlockStorage
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import javax.annotation.ParametersAreNonnullByDefault

@Suppress("unused", "LeakingThis")
abstract class SContainer @ParametersAreNonnullByDefault protected constructor(
    itemGroup: ItemGroup?, item: SlimefunItemStack?, recipeType: RecipeType?, recipe: Array<ItemStack?>?
) : SlimefunItem(itemGroup!!, item!!, recipeType!!, recipe!!), InventoryBlock, EnergyNetComponent,
    MachineProcessHolder<CraftingOperation> {
    private val recipes: MutableList<MachineRecipe> = ArrayList()
    private val processor = MachineProcessor(this)
    private var energyConsumption = -1
    private var energyCapacity = -1
    private var speed = -1

    init {
        processor.progressBar = progressBar
        createPreset(this, inventoryTitle) { preset: BlockMenuPreset ->
            constructMenu(
                preset
            )
        }
        addItemHandler(onBlockBreak())
    }

    private fun onBlockBreak(): BlockBreakHandler {
        return object : SimpleBlockBreakHandler() {
            override fun onBlockBreak(b: Block) {
                val inv = BlockStorage.getInventory(b)
                if (inv != null) {
                    inv.dropItems(b.location, *inputSlots)
                    inv.dropItems(b.location, *outputSlots)
                    inv.dropItems(b.location, *SLOTS_UPGRADE_MODULE)
                }
                processor.endOperation(b)
            }
        }
    }

    override fun getMachineProcessor(): MachineProcessor<CraftingOperation> {
        return processor
    }

    private fun constructMenu(preset: BlockMenuPreset) {
        for (i in BORDER) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler())
        }
        for (i in BORDER_IN) {
            preset.addItem(i, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler())
        }
        for (i in BORDER_OUT) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler())
        }
        preset.addItem(
            SLOT_INDICATOR,
            CustomItemStack(Material.PAPER, "&e电量指示器&f", "", "&f容"),
            ChestMenuUtils.getEmptyClickHandler()
        )
        preset.addItem(SLOT_SETTING, CustomItemStack(Material.BONE, "&e机器设置", ""))
        preset.addItem(
            SLOT_PROCESS, CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler()
        )
        for (i in outputSlots) {
            preset.addMenuClickHandler(i, object : AdvancedMenuClickHandler {
                override fun onClick(p: Player, slot: Int, cursor: ItemStack, action: ClickAction): Boolean {
                    return false
                }

                override fun onClick(
                    e: InventoryClickEvent, p: Player, slot: Int, cursor: ItemStack, action: ClickAction
                ): Boolean {
                    return cursor.type == Material.AIR
                }
            })
        }
    }

    private val inventoryTitle: String
        get() = itemName
    abstract val progressBar: ItemStack?

    override fun getCapacity(): Int {
        return energyCapacity
    }

    fun setCapacity(capacity: Int): SContainer {
        Validate.isTrue(capacity > 0, "The capacity must be greater than zero!")
        return if (state == ItemState.UNREGISTERED) {
            energyCapacity = capacity
            this
        } else {
            throw IllegalStateException("You cannot modify the capacity after the Item was registered.")
        }
    }

    fun setProcessingSpeed(speed: Int): SContainer {
        Validate.isTrue(speed > 0, "The speed must be greater than zero!")
        this.speed = speed
        return this
    }

    fun setEnergyConsumption(energyConsumption: Int): SContainer {
        Validate.isTrue(energyConsumption > 0, "The energy consumption must be greater than zero!")
        Validate.isTrue(energyCapacity > 0, "You must specify the capacity before you can set the consumption amount.")
        Validate.isTrue(
            energyConsumption <= energyCapacity,
            "The energy consumption cannot be higher than the capacity ($energyCapacity)"
        )
        this.energyConsumption = energyConsumption
        return this
    }

    override fun register(addon: SlimefunAddon) {
        this.addon = addon
        if (capacity <= 0) {
            warn("The capacity has not been configured correctly. The Item was disabled.")
            warn("Make sure to call '" + javaClass.simpleName + "#setEnergyCapacity(...)' before registering!")
        }
        if (energyConsumption <= 0) {
            warn("The energy consumption has not been configured correctly. The Item was disabled.")
            warn("Make sure to call '" + javaClass.simpleName + "#setEnergyConsumption(...)' before registering!")
        }
        if (speed <= 0) {
            warn("The processing speed has not been configured correctly. The Item was disabled.")
            warn("Make sure to call '" + javaClass.simpleName + "#setProcessingSpeed(...)' before registering!")
        }
        if (capacity > 0 && energyConsumption > 0 && speed > 0) {
            super.register(addon)
        }
        registerDefaultRecipes()
    }

    abstract val machineIdentifier: String?

    private fun registerDefaultRecipes() {
        // Override this method to register your machine recipes
    }

    val machineRecipes: List<MachineRecipe>
        get() = recipes
    val displayRecipes: List<ItemStack>
        get() {
            val displayRecipes: MutableList<ItemStack> = ArrayList(recipes.size * 2)
            for (recipe in recipes) {
                if (recipe.input.size != 1) {
                    continue
                }
                displayRecipes.add(recipe.input[0])
                displayRecipes.add(recipe.output[0])
            }
            return displayRecipes
        }

    override fun getInputSlots(): IntArray {
        return SLOT_INPUT
    }

    override fun getOutputSlots(): IntArray {
        return SLOT_OUTPUT
    }

    override fun getEnergyComponentType(): EnergyNetComponentType {
        return EnergyNetComponentType.CONSUMER
    }

    private fun registerRecipe(recipe: MachineRecipe) {
        recipe.ticks = recipe.ticks / speed
        recipes.add(recipe)
    }

    fun registerRecipe(seconds: Int, input: Array<ItemStack?>?, output: Array<ItemStack?>?) {
        registerRecipe(MachineRecipe(seconds, input, output))
    }

    fun registerRecipe(seconds: Int, input: ItemStack, output: ItemStack) {
        registerRecipe(MachineRecipe(seconds, arrayOf(input), arrayOf(output)))
    }

    override fun preRegister() {
        addItemHandler(object : BlockTicker() {
            override fun tick(b: Block, sf: SlimefunItem, data: Config) {
                this@SContainer.tick(b)
            }

            override fun isSynchronized(): Boolean {
                return false
            }
        })
    }

    protected fun tick(b: Block) {
        val inv = BlockStorage.getInventory(b)
        var currentOperation = processor.getOperation(b)
        if (currentOperation != null) {
            if (takeCharge(b.location)) {
                if (!currentOperation.isFinished) {
                    processor.updateProgressBar(inv, SLOT_PROCESS, currentOperation)
                    currentOperation.addProgress(1)
                } else {
                    inv.replaceExistingItem(SLOT_PROCESS, CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "))
                    for (output in currentOperation.results) {
                        inv.pushItem(output.clone(), *outputSlots)
                    }
                    processor.endOperation(b)
                }
            }
        } else {
            val next = findNextRecipe(inv)
            if (next != null) {
                currentOperation = CraftingOperation(next)
                processor.startOperation(b, currentOperation)

                // Fixes #3534 - Update indicator immediately
                processor.updateProgressBar(inv, SLOT_PROCESS, currentOperation)
            }
        }
    }

    private fun takeCharge(l: Location?): Boolean {
        Validate.notNull(l, "Can't attempt to take charge from a null location!")
        return if (isChargeable) {
            val charge = getCharge(l!!)
            if (charge < energyConsumption) {
                return false
            }
            setCharge(l, charge - energyConsumption)
            true
        } else {
            true
        }
    }

    private fun findNextRecipe(inv: BlockMenu): MachineRecipe? {
        val inventory: MutableMap<Int, ItemStack> = HashMap()
        for (slot in inputSlots) {
            val item = inv.getItemInSlot(slot)
            if (item != null) {
                inventory[slot] = ItemStackWrapper.wrap(item)
            }
        }
        val found: MutableMap<Int, Int> = HashMap()
        for (recipe in recipes) {
            for (input in recipe.input) {
                for (slot in inputSlots) {
                    if (SlimefunUtils.isItemSimilar(inventory[slot], input, true)) {
                        found[slot] = input.amount
                        break
                    }
                }
            }
            if (found.size == recipe.input.size) {
                if (!InvUtils.fitAll(inv.toInventory(), recipe.output, *outputSlots)) {
                    return null
                }
                for ((key, value) in found) {
                    inv.consumeItem(key, value)
                }
                return recipe
            } else {
                found.clear()
            }
        }
        return null
    }

    companion object {
        private val BORDER = intArrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 31, 49
        )
        private val BORDER_IN = intArrayOf(
            27, 28, 29, 30, 36, 45, 46, 47, 48
        )
        private val BORDER_OUT = intArrayOf(
            32, 33, 34, 35, 44, 50, 51, 52, 53
        )
        private val SLOTS_UPGRADE_MODULE = intArrayOf(
            10, 11, 12
        )
        private val SLOT_INPUT = intArrayOf(
            37, 38, 39
        )
        private val SLOT_OUTPUT = intArrayOf(
            41, 42, 43
        )
        private const val SLOT_SWITCH = 14
        private const val SLOT_INDICATOR = 15
        private const val SLOT_SETTING = 16
        private const val SLOT_PROCESS = 40
    }
}
