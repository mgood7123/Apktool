/**
 *  Copyright (C) 2018 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2018 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.androlib.res.decoder;

import brut.androlib.AndrolibException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class ResStreamDecoderContainer {
    private final Map<String, ResStreamDecoder> mDecoders = new HashMap<String, ResStreamDecoder>();

    public void decode(InputStream in, OutputStream out, String decoderName)
            throws AndrolibException {
        getDecoder(decoderName).decode(in, out);
    }

    public ResStreamDecoder getDecoder(String name) throws AndrolibException {
        // android cannot decode 9patch due to java.awt being
        // unsupported, so throw an exception if attempted to
        // decode
         if (name.contentEquals("9patch")) {
             throw new AndrolibException(
                     "9patch is not yet supported by the decoder for Android, " +
                             "consider using the desktop version of apktool instead");
         }
        ResStreamDecoder decoder = mDecoders.get(name);
        if (decoder == null) {
            throw new AndrolibException("Undefined decoder: " + name);
        }
        return decoder;
    }

    public void setDecoder(String name, ResStreamDecoder decoder) {
        mDecoders.put(name, decoder);
    }
}
