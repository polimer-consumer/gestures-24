import com.polimerconsumer.gestures24.EntrySide
import com.polimerconsumer.gestures24.Meme
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.swing.JFrame
import java.awt.event.MouseEvent
import javax.imageio.ImageIO.read
import javax.swing.ImageIcon
import kotlin.test.assertEquals

class MemeApplicationTest {
    private lateinit var frame: JFrame
    private lateinit var meme: Meme

    @BeforeEach
    fun setUp() {
        frame = JFrame().apply {
            setSize(1080, 720)
            isVisible = true
        }

        val imageStream = this::class.java.getResourceAsStream("/memes/tigrib.png")
            ?: throw IllegalArgumentException("Image not found")
        val image = read(imageStream)
        val memeIcon = ImageIcon(image)

        meme = Meme(memeIcon, frame)
    }

    @Test
    fun testEntrySide() {
        val frameBounds = frame.bounds
        val insets = frame.insets

        // Mock mouse entering from left
        val eventFromLeft = MouseEvent(frame, 0, 0, 0, 0, 360, 0, false)
        meme.onMouseEnter(eventFromLeft.x, eventFromLeft.y, EntrySide.LEFT)
        assertEquals(EntrySide.LEFT, getEntrySide(eventFromLeft))

        // Mock mouse entering from right
        val eventFromRight = MouseEvent(frame, 0, 0, 0, frameBounds.width - 1, 360, 0, false)
        meme.onMouseEnter(eventFromRight.x, eventFromRight.y, EntrySide.RIGHT)
        assertEquals(EntrySide.RIGHT, getEntrySide(eventFromRight))

        // Mock mouse entering from top
        val eventFromTop = MouseEvent(frame, 0, 0, 0, 360, insets.top + 1, 0, false)
        meme.onMouseEnter(eventFromTop.x, eventFromTop.y, EntrySide.TOP)
        assertEquals(EntrySide.TOP, getEntrySide(eventFromTop))

        // Mock mouse entering from bottom
        val eventFromBottom = MouseEvent(frame, 0, 0, 0, 360, frameBounds.height - 1, 0, false)
        meme.onMouseEnter(eventFromBottom.x, eventFromBottom.y, EntrySide.BOTTOM)
        assertEquals(EntrySide.BOTTOM, getEntrySide(eventFromBottom))
    }

    @Test
    fun testScaling() {
        // Mock mouse to move within the frame
        meme.onMouseEnter(100, 100, EntrySide.LEFT)
        assertEquals(0.25, meme.scale)

        // Mock mouse movement to the right
        meme.onMouseMove(200, 100)
        assert(meme.scale > 0.25)

        // Mock mouse movement in reverse direction
        meme.onMouseMove(100, 100)
        assert(meme.scale == 0.25)

        // Mock mouse movement in orthogonal direction
        meme.onMouseMove(100, 400)
        assert(meme.scale == 0.25)

        // Check scale respects boundaries
        meme.onMouseMove(900, 100) // (900 - 100) * 0.001 + 0.25 == 1.05
        assertEquals(1.0, meme.scale) // Should clip at full size
    }

    private fun getEntrySide(event: MouseEvent): EntrySide {
        val bounds = frame.bounds
        val insets = frame.insets

        val distanceToLeft = event.x
        val distanceToRight = bounds.width - event.x
        val distanceToTop = event.y - insets.top
        val distanceToBottom = bounds.height - event.y

        return when (minOf(distanceToLeft, distanceToRight, distanceToTop, distanceToBottom)) {
            distanceToLeft -> EntrySide.LEFT
            distanceToRight -> EntrySide.RIGHT
            distanceToTop -> EntrySide.TOP
            distanceToBottom -> EntrySide.BOTTOM
            else -> EntrySide.UNDEFINED
        }
    }
}
