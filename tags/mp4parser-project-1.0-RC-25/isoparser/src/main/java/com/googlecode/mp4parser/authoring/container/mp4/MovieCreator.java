/*
 * Copyright 2012 Sebastian Annies, Hamburg
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
package com.googlecode.mp4parser.authoring.container.mp4;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Shortcut to build a movie from an MP4 file.
 */
public class MovieCreator {

    public static Movie build(String file) throws IOException {
        return build(new FileInputStream(file).getChannel());
    }

    /**
     * Creates <code>Movie</code> object from a <code>ReadableByteChannel</code>.
     *
     * @param channel input channel
     * @return a representation of the movie
     * @throws IOException in case of I/O error during IsoFile creation
     */
    public static Movie build(FileChannel channel) throws IOException {
        IsoFile isoFile = new IsoFile(channel);
        Movie m = new Movie();
        List<TrackBox> trackBoxes = isoFile.getMovieBox().getBoxes(TrackBox.class);
        for (TrackBox trackBox : trackBoxes) {
            m.addTrack(new Mp4TrackImpl(trackBox));
        }
        m.setMatrix(isoFile.getMovieBox().getMovieHeaderBox().getMatrix());
        return m;
    }
}
