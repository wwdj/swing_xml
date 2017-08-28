
import com.wang.ComponentView
import com.wang.MyFrame
import java.awt.event.ActionListener
import java.io.File

/**
 * Created by MR.jian on 2017/8/28.
 */

fun main(args: Array<String>) {
    val f = MyFrame("zz")
    f.inflate(File("src/asd.xml"),400,300)
    f.addListener("bb", ActionListener {
        println(f.getText("aa"))
    })
}