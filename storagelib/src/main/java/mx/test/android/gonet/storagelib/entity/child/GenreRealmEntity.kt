package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class GenreRealmEntity: RealmObject() {
    var id: Int = -1
    var name: String = ""
}