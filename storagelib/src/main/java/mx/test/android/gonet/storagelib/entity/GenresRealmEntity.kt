package mx.test.android.gonet.storagelib.entity

import io.realm.RealmList
import io.realm.RealmObject
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity

open class GenresRealmEntity: RealmObject() {
    var genres: RealmList<GenreRealmEntity> = RealmList()
}