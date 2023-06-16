import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData
import io.sn.dumortierite.registry.ProgramRegistry
import io.sn.dumortierite.utils.AbstractProgram
import io.sn.dumortierite.utils.SpecificProgramType
import org.bukkit.Location
import org.bukkit.Particle
import org.jetbrains.annotations.NotNull

//noinspection GrUnresolvedAccess
ProgramRegistry reg = registry

static def buildName(String color, String name, boolean bold) {
    return "<" + color + (bold ? "><bold>" : ">") + name + "<reset>"
}

reg.registerProgram(new AbstractProgram(SpecificProgramType.DEFAULT, 0, buildName("white", "空程序", false), "<white>没什么用的空程序<newline>只能在机器旁边显示一点粒子效果") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "0")
        l.getWorld().spawnParticle(Particle.END_ROD, 0.5, 0.5, 0.5, 5)
    }
})

reg.registerProgram(new AbstractProgram(SpecificProgramType.COAL_GENERATOR, 1, buildName("color:#F5F5F5", "煤发电机马达控制程序", false), "<white>使煤发电机可以正常工作") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "1")
    }
})

reg.registerProgram(new AbstractProgram(SpecificProgramType.COAL_GENERATOR_ADV, 3, buildName("red", "优化煤发电机马达控制程序", false), "<white>优化后的程序<newline>可以使发电机效率整整提高一倍") {
    @Override
    void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
        data.setData("progressive", "2")
    }
})