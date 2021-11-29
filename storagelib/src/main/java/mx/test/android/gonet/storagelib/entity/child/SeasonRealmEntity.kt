package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class SeasonRealmEntity: RealmObject() {
    var air_date: String  = ""
    var episode_count: Int  = -1
    var id: Int  = -1
    var name: String  = ""
    var overview: String  = ""
    var poster_path: String  = ""
    var season_number: Int  = -1
}