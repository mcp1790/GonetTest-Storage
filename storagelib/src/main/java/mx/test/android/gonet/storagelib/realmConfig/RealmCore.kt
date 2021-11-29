package mx.test.android.gonet.storagelib.realmConfig

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmError
import io.realm.rx.RealmObservableFactory

class RealmCore {
    companion object {
        fun getRxInstance(context: Context): Realm {
            Realm.init(context)
            return Realm.getInstance(this.getRxConfiguration())
        }

        private fun getRxConfiguration(): RealmConfiguration {
            val key = ByteArray(64)
            val config = RealmConfiguration.Builder()
                .encryptionKey(key)
                .schemaVersion(5)
                .rxFactory(RealmObservableFactory(false))
                .build()

            Realm.setDefaultConfiguration(config)

            return config
        }

        fun deleteAllReference(context: Context) {
            try {
                this.getRxInstance(context).close()
                Realm.init(context)
                Realm.deleteRealm(this.getRxConfiguration())
            }
            catch (e: RealmError) {
                if (e.message?.contains("Permission denied") == true) {
                    Realm.deleteRealm(this.getRxConfiguration())
                    Realm.getInstance(this.getRxConfiguration())
                }
            }
        }
    }
}