import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData
import io.sn.dumortierite.registry.ProgramRegistry
import io.sn.dumortierite.utils.AbstractProgram
import io.sn.dumortierite.utils.SpecificProgramType
import org.bukkit.Location
import org.jetbrains.annotations.NotNull

//noinspection GrUnresolvedAccess
ProgramRegistry reg = registry

static def buildName(String color, String name, boolean bold) {
    return "<" + color + (bold ? "><bold>" : ">") + name + "<reset>"
}

reg.registerProgram("DEFAULT", new AbstractProgram(SpecificProgramType.DEFAULT, 0, buildName("white", "空程序", false), "<white>没什么用的空程序") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "0")
    }
})

reg.registerProgram("COAL_GENERATOR", new AbstractProgram(SpecificProgramType.COAL_GENERATOR, 1, buildName("color:#F5F5F5", "煤发电机马达控制程序", false), "<white>使煤发电机可以正常工作") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "1")
    }
})

reg.registerProgram("COAL_GENERATOR_ADV", new AbstractProgram(SpecificProgramType.COAL_GENERATOR, 3, buildName("red", "煤发电机优化马达控制程序", false), "<white>优化后的程序<newline>可以使发电机效率整整提高一倍") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "2")
    }
})

reg.registerProgram("BIOMASS_GENERATOR", new AbstractProgram(SpecificProgramType.BIOMASS_GENERATOR, 1, buildName("color:#F5F5F5", "生物质发电机马达控制程序", false), "<white>使生物质发电机可以正常工作") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "1")
    }
})

reg.registerProgram("BIOMASS_GENERATOR_ADV", new AbstractProgram(SpecificProgramType.BIOMASS_GENERATOR, 3, buildName("red", "生物质发电机优化马达控制程序", false), "<white>优化后的程序<newline>可以使发电机效率整整提高一倍") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "2")
    }
})

reg.registerProgram("BLACK_METAL_COAL_GENERATOR", new AbstractProgram(SpecificProgramType.COAL_GENERATOR, 4, buildName("red", "合金煤发电机马达控制程序", false), "<white>高度优化的程序<newline>使合金煤发电机可以正常工作") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "1")
    }
})
