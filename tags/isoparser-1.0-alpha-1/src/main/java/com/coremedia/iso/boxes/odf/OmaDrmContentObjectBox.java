/*  
 * Copyright 2008 CoreMedia AG, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an AS IS BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.coremedia.iso.boxes.odf;

import com.coremedia.iso.BoxFactory;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoInputStream;
import com.coremedia.iso.IsoOutputStream;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.FullBox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OmaDrmContentObjectBox extends FullBox {
  private long omaDrmDataLength;
  private byte[] omaDrmData;
  public static final String TYPE = "odda";

  public byte[] getHeader() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      IsoOutputStream ios = new IsoOutputStream(baos, false);
      ios.writeUInt32(1);
      ios.write(getType());
      ios.writeUInt64(getSize());
      ios.writeUInt8(getVersion());
      ios.writeUInt24(getFlags());

      assert baos.size() == getHeaderSize();
      return baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected long getHeaderSize() {
    return 20;
  }

  public OmaDrmContentObjectBox() {
    super(IsoFile.fourCCtoBytes(TYPE));
  }

  public long getOmaDrmDataLength() {
    return omaDrmDataLength;
  }

  public byte[] getOmaDrmData() {
    return omaDrmData;
  }

  public String getDisplayName() {
    return "OMA DRM Content Object Box";
  }

  protected long getContentSize() {
    return 8 + omaDrmDataLength;
  }

  public void parse(IsoInputStream in, long size, BoxFactory boxFactory, Box lastMovieFragmentBox) throws IOException {
    super.parse(in, size, boxFactory, lastMovieFragmentBox);
    omaDrmDataLength = in.readUInt64();
    omaDrmData = in.read((int) omaDrmDataLength);
    assert size == 12 + omaDrmDataLength;
  }

  protected void getContent(IsoOutputStream os) throws IOException {
    os.writeUInt64(omaDrmDataLength);
    os.write(omaDrmData);
  }

  public String toString() {
    return "OmaDrmContentObjectBox[omaDrmDataLength=" + getOmaDrmDataLength() + "]";
  }
}
