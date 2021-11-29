package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class SpokenLanguageRealmEntity: RealmObject() {
    var english_name: String = ""
    var iso_639_1: String = ""
    var name: String = ""
}