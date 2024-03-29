package com.coremedia.iso.boxes.apple;

import com.coremedia.iso.Utf8;

/**
 * iTunes Artist box.
 */
public final class AppleArtistBox extends AbstractAppleMetaDataBox {
  public static final String TYPE = "\u00a9ART";


  public AppleArtistBox() {
    super(TYPE);
  }

  public String getDisplayName() {
    return "iTunes Artist";
  }


  public void setArtist(String comment) {
    appleDataBox = new AppleDataBox();
    appleDataBox.setVersion(0);
    appleDataBox.setFlags(1);
    appleDataBox.setFourBytes(new byte[4]);
    appleDataBox.setContent(Utf8.convert(comment));
  }

  public String getArtist() {
    return Utf8.convert(appleDataBox.getContent());
  }

}
