package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class DatesRealmEntity: RealmObject() {
    var maximum: String = ""
    var minimum: String = ""
}