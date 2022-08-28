// Copyright 2021 Code Intelligence GmbH
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.code_intelligence.jazzer.junit.FuzzTest;

import java.util.Base64;

class EncryptionFuzzer {
  private static String base64(byte[] input) {
    return Base64.getEncoder().encodeToString(input);
  }

  private static long insecureEncrypt(long input) {
    long key = 0xefe4eb93215cb6b0L;
    return input ^ key;
  }

  @FuzzTest
  void breakEncryption(FuzzedDataProvider data) {
    if (base64(data.consumeBytes(6)).equals("SmF6emVy")) {
      long[] plaintextBlocks = data.consumeLongs(2);
      if (plaintextBlocks.length != 2)
        return;
      if (insecureEncrypt(plaintextBlocks[0]) == 0x9fc48ee64d3dc090L) {
        if (insecureEncrypt(plaintextBlocks[1]) == 0x888a82ff483ad9c2L) {
          mustNeverBeCalled();
        }
      }
    }
  }

  private static void mustNeverBeCalled() {
    throw new FuzzerSecurityIssueLow("mustNeverBeCalled has been called");
  }
}
