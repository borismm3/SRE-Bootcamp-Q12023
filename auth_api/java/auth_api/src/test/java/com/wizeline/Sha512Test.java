package com.wizeline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Sha512Test {
    @Test
    void get_SHA_512_SecurePassword() {
        Assertions.assertEquals(
                "15e24a16abfc4eef5faeb806e903f78b188c30e4984a03be4c243312f198d1229ae8759e98993464cf713e3683e891fb3f04fbda9cc40f20a07a58ff4bb00788",
                Sha512.get_SHA_512_SecurePassword("secret", "F^S%QljSfV"));

        Assertions.assertEquals(
                "89155af89e8a34dcbde088c72c3f001ac53486fcdb3946b1ed3fde8744ac397d99bf6f44e005af6f6944a1f7ed6bd0e2dd09b8ea3bcfd3e8862878d1709712e5",
                Sha512.get_SHA_512_SecurePassword("noPow3r", "KjvFUC#K*i"));

        Assertions.assertEquals(
                "dc2c9606ce2aefd75c5cfa3035394f7780214d2a2e8889d1d824bb9a131b61ed460fa323807d0fe42a2cb5b2e57b668bb288631cdd897967fb37e213a694cafd",
                Sha512.get_SHA_512_SecurePassword("thisIsNotAPasswordBob", "ykptwoT=M("));
    }
}
