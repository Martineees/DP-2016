/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.lepko.martin.arquiz.Utils;

import java.nio.Buffer;


public class PlaneObject extends MeshObject
{
    // Data for drawing the 3D plane as overlay
    private static final double planeVertices[]  = {
            -50f, -50f, 0.0f, 50f, -50f, 0.0f, 50f, 50f, 0.0f, -50f, 50f, 0.0f
    };

    private static final double planeTexcoords[] = {
            0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0
    };


    private static final double planeNormals[]   = {
            0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0
    };

    private static final short planeIndices[]   = {
            0, 1, 2, 0, 2, 3
    };

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;


    public PlaneObject()
    {
        mVertBuff = fillBuffer(planeVertices);
        mTexCoordBuff = fillBuffer(planeTexcoords);
        mNormBuff = fillBuffer(planeNormals);
        mIndBuff = fillBuffer(planeIndices);
    }
    
    
    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
            default:
                break;
        }
        return result;
    }
    
    
    @Override
    public int getNumObjectVertex()
    {
        return planeVertices.length / 3;
    }
    
    
    @Override
    public int getNumObjectIndex()
    {
        return planeIndices.length;
    }
}
