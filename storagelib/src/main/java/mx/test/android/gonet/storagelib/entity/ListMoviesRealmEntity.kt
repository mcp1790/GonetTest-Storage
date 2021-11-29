package mx.test.android.gonet.storagelib.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import mx.test.android.gonet.storagelib.entity.child.DatesRealmEntity

open class ListMoviesRealmEntity: RealmObject(){
    @PrimaryKey
    var page: Int = -1
    var total_pages: Int = -1
    var total_results: Int = -1
    var results: RealmList<MovieRawRealmEntity>? = RealmList()
    var dates: DatesRealmEntity = DatesRealmEntity()
}