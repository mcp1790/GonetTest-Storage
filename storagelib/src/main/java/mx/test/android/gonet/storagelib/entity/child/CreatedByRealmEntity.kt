package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CreatedByRealmEntity: RealmObject() {
    @PrimaryKey
    var id: Int = -1
    var credit_id: String = ""
    var gender: Int = -1
    var name: String = ""
    var profile_path: String = ""
}