import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.CvType
import org.opencv.core.Scalar
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import org.opencv.utils.Converters
import org.opencv.core.CvType
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.Color
import javax.swing.JFrame
import java.awt.Graphics

public class VideoFeed extends JFrame {

    def image
    def brush

    VideoFeed() {
        this.setVisible(true)
        this.setSize(640,480)
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
        this.setLayout(null)
        brush = this.getGraphics()
    }

    def toBufferedImage(Mat m){
         def type
         ( m.channels() > 1 ) ? (type = BufferedImage.TYPE_3BYTE_BGR) : (type = BufferedImage.TYPE_BYTE_GRAY)
          def bufferSize = m.channels()*m.cols()*m.rows()
          byte [] b = new byte[bufferSize]
          m.get(0,0,b)
          def image = new BufferedImage(m.cols(),m.rows(), type)
          final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData()
          System.arraycopy(b, 0, targetPixels, 0, b.length)
          return image
      }

      def playStream() {
          def vidSrc = new VideoCapture(0)
          println vidSrc.get(Videoio.CAP_PROP_FORMAT)
          vidSrc.set(Videoio.CV_CAP_PROP_FRAME_WIDTH , 640);
          vidSrc.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT , 480);
          vidSrc.set(Videoio.CAP_PROP_CONVERT_RGB, 1);

          for (;;){
              def frame = new Mat()
              vidSrc.read(frame)
              frame.convertTo(frame, CvType.CV_8SC3)
              image = toBufferedImage(frame)
              frame.release()
              this.update(brush)
              //ImageIO.write(toBufferedImage(frame), "png", new File("testImage${i}"))
          }
          vidSrc.release()
      }

    def render(){
        this.validate()
        this.repaint()
    }

    void update(Graphics g) {
        g.drawImage(image, 0, 0, null)
    }

}


public static main(String[] args) {
    Runtime.getRuntime().loadLibrary0(groovy.lang.GroovyClassLoader.class, Core.NATIVE_LIBRARY_NAME)
    println "HELLO"
    System.out.println("Welcome to OpenCV " + Core.VERSION);
    def vs = new VideoFeed()
    vs.playStream()
}
