package org.example;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserPrivateKeyInfo;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserRequestInfo;

import java.util.ArrayList;

public class IitSignatureService {

    private EndUser endUser;
    private String publicKey;
    private String csrStr;
    private byte[] privateKey;

    public IitSignatureService() {
        init();
        createKeys();
    }

    public void init() {
        endUser = new EndUser();

        try {
            endUser.SetUIMode(false);
            endUser.SetLanguage(EndUser.EU_EN_LANG);
            endUser.SetCharset("UTF-8");
            endUser.Initialize();
            endUser.SetRuntimeParameter(EndUser.EU_LOG_ENCODING_PARAMETER, EndUser.EU_ENCODING_UTF8);
            endUser.SetRuntimeParameter(EndUser.EU_SAVE_SETTINGS_PARAMETER, EndUser.EU_SETTINGS_ID_NONE);
            endUser.SetRuntimeParameter(EndUser.EU_SIGN_TYPE_PARAMETER, EndUser.EU_SIGN_TYPE_CADES_X_LONG);
            endUser.SetModeSettings(endUser.CreateModeSettings());
            endUser.SetOCSPSettings(endUser.CreateOCSPSettings());
            endUser.SetOCSPAccessInfoModeSettings(endUser.CreateOCSPAccessInfoModeSettings());
            endUser.SetOCSPAccessInfoSettings(endUser.CreateOCSPAccessInfoSettings());
            endUser.SetTSPSettings(endUser.CreateTSPSettings());
            endUser.SetProxySettings(endUser.CreateProxySettings());

//        EndUserFileStoreSettings endUserFileStoreSettings = endUser.CreateFileStoreSettings();
//        endUserFileStoreSettings.SetPath(storageDirectory.absolutePath);
//        endUserFileStoreSettings.SetSaveLoadedCerts(true);
//
//        endUser.SetFileStoreSettings(endUserFileStoreSettings);

            endUser.SetLDAPSettings(endUser.CreateLDAPSettings());
            endUser.SetCMPSettings(endUser.CreateCMPSettings());
        } catch (Exception e) {
            throw new RuntimeException("!!!");
        }
    }

    public void createKeys() {
        EndUserPrivateKeyInfo endUserPrivateKeyInfo = new EndUserPrivateKeyInfo();
        ArrayList request;
        try {
            request = endUser.GeneratePrivateKey2(
                    "",
                    EndUser.EU_KEYS_TYPE_NONE,
                    0,
                    false,
                    0,
                    "",
                    EndUser.EU_KEYS_TYPE_ECDSA_WITH_SHA,
                    0,
                    "",
                    EndUser.EU_KEYS_LENGTH_DS_ECDSA_256,
                    "",
                    null,
                    "",
                    endUserPrivateKeyInfo
            );

            EndUserRequestInfo endUserRequestInfo = (EndUserRequestInfo) request.get(0);

            this.publicKey = endUserRequestInfo.GetPublicKey().trim();
            this.csrStr = endUser.BASE64Encode(endUserRequestInfo.GetRequest());
            this.privateKey = endUserPrivateKeyInfo.GetPrivateKey();
//            String privateKeyEncoded = endUser.BASE64Encode(privateKey);

        } catch (Exception ignored) {
            throw new RuntimeException("Can't generate IIT Private and Public key.");
        }
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getCsrStr() {
        return csrStr;
    }
}
