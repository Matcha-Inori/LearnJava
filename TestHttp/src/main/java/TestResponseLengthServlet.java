import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Administrator on 2016/10/20.
 */
public class TestResponseLengthServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        URL htmlUrl = this.getServletContext().getResource("/html/TestResponseHtml.html");
        File htmlFile = null;
        try
        {
            htmlFile = new File(htmlUrl.toURI());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(htmlFile, "r");
                FileChannel fileChannel = randomAccessFile.getChannel();
                OutputStream outputStream = resp.getOutputStream();
                WritableByteChannel writableByteChannel = Channels.newChannel(outputStream)
        )
        {
            fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try (
                OutputStream outputStream = resp.getOutputStream();
                WritableByteChannel writableByteChannel = Channels.newChannel(outputStream)
        )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
            byteBuffer.put("abc".getBytes());
            byteBuffer.flip();
            writableByteChannel.write(byteBuffer);
        }
    }
}
