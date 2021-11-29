package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class ProductionCountryRealmEntity: RealmObject() {
    var iso_3166_1: String = ""
    var name: String = ""
}
