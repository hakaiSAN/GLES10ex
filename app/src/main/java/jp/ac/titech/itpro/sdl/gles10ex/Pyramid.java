package jp.ac.titech.itpro.sdl.gles10ex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid implements SimpleRenderer.Obj {
    private final static String TAG = "Pyramid";

    private FloatBuffer vbuf, fVbobuf, fVrbuf, fVlbuf, fVbabuf;
    private ShortBuffer cbuf;
    private float x, y, z;



    public Pyramid(float s, float x, float y, float z) {
        float[] vertices = {
                // bottom
                -s, 0, -s,
                s, 0, -s,
                -s, 0, s,
                // left
                -s, 0, -s,
                0, s, 0,
                -s, 0, s,
                // right
                -s, 0, s,
                0, s, 0,
                s, 0, -s,
                // back
                s, 0, -s,
                0, s, 0,
                -s, 0, -s,
        };

        float [][] v = new float[4][]; //頂点
        v[0] = new float[] { -s, 0, -s } ;
        v[1] = new float[] { s, 0, -s } ;
        v[2] = new float[] { -s, 0, s } ;
        v[3] = new float[] { 0, s, 0} ;

        float[] faceVerticalbottom = Cross(
                new float[]{ v[1][0] - v[0][0], v[1][1] - v[0][1], v[1][2] - v[0][2] }, //「v[1] - v[0]」
                new float[]{ v[2][0] - v[0][0], v[2][1] - v[0][1], v[2][2] - v[0][2] }  //「v[2] - v[0]」
        );
        Log.d(TAG, "faceVerticalbottom" + faceVerticalbottom[0] + faceVerticalbottom[1] + faceVerticalbottom[2]) ;

        float[] faceVerticalright = Cross(
                new float[]{ v[1][0] - v[0][0], v[1][1] - v[0][1], v[1][2] - v[0][2] }, //「v[1] - v[0]」
                new float[]{ v[3][0] - v[0][0], v[3][1] - v[0][1], v[3][2] - v[0][2] }  //「v[3] - v[0]」
        );//
        Log.d(TAG, "faceVerticalright" + faceVerticalright[0] + faceVerticalright[1] + faceVerticalright[2]) ;

        float[] faceVerticalleft = Cross(
                new float[]{ v[2][0] - v[0][0], v[2][1] - v[0][1], v[2][2] - v[0][2] }, //「v[2] - v[0]」
                new float[]{ v[3][0] - v[0][0], v[3][1] - v[0][1], v[3][2] - v[0][2] }  //「v[3] - v[0]」
        );
        Log.d(TAG, "faceVerticalleft" + faceVerticalleft[0] + faceVerticalleft[1] + faceVerticalleft[2]) ;

        float[] faceVerticalback = Cross(
                new float[]{ v[2][0] - v[1][0], v[2][1] - v[1][1], v[2][2] - v[1][2] }, //「v[2] - v[1]」
                new float[]{ v[3][0] - v[1][0], v[3][1] - v[1][1], v[3][2] - v[1][2] }  //「v[3] - v[1]」
        );
        Log.d(TAG, "faceVerticalback" + faceVerticalback[0] + faceVerticalback[1] + faceVerticalback[2]) ;


        //  short[] colors = {
//            255, 255, 0, 255,
//            0, 255, 255, 255,
//            0, 0, 0, 0,
//            255, 0, 255, 255,
//        };

        vbuf = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vbuf.put(vertices);
        vbuf.position(0);

        fVbobuf = ByteBuffer.allocateDirect(faceVerticalbottom.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fVbobuf.put(faceVerticalbottom);
        fVbobuf.position(0);

        fVrbuf = ByteBuffer.allocateDirect(faceVerticalleft.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fVrbuf.put(faceVerticalright);
        fVrbuf.position(0);

        fVlbuf = ByteBuffer.allocateDirect(faceVerticalright.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fVlbuf.put(faceVerticalright);
        fVlbuf.position(0);

        fVbabuf= ByteBuffer.allocateDirect(faceVerticalback.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fVbabuf.put(faceVerticalback);
        fVbabuf.position(0);

//        cbuf = ByteBuffer.allocateDirect(colors.length*2)
//                .order(ByteOrder.nativeOrder()).asShortBuffer();
//        cbuf.put(colors);
//        cbuf.position(0);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnable(GL10.GL_NORMALIZE);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vbuf);


        // bottom
//        gl.glNormal3f(0, -1, 0);
        gl.glNormal3f(fVbobuf.get(0), fVbobuf.get(1), fVbobuf.get(2));
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // left
//        gl.glNormal3f(-0.5f, 1, 0);
        gl.glNormal3f(fVlbuf.get(0), fVlbuf.get(1), fVlbuf.get(2));
        gl.glDrawArrays(GL10.GL_TRIANGLES, 3, 3);

        // right
//        gl.glNormal3f(0.5f, 1, 0);
        gl.glNormal3f(fVrbuf.get(0), fVrbuf.get(1), fVrbuf.get(2));
        gl.glDrawArrays(GL10.GL_TRIANGLES, 6, 3);

        // back
//        gl.glNormal3f(0, 1, 0.5f);
//        Log.d(TAG, "backbuf" + fVbabuf.get(0) + fVbabuf.get(1) + fVbabuf.get(2));
        gl.glNormal3f(fVbabuf.get(0), fVbabuf.get(1), fVbabuf.get(2));
        gl.glDrawArrays(GL10.GL_TRIANGLES, 9, 3);

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }
    public static float[] Cross( float[] vector1, float[] vector2 ) {
        return new float[]{
                vector1[1] * vector2[2] - vector1[2] * vector2[1],
                vector1[2] * vector2[0] - vector1[0] * vector2[2],
                vector1[0] * vector2[1] - vector1[1] * vector2[0]
        };
    }
}
