package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class LastEpisodeToAirRealmEntity: RealmObject() {
    var air_date: String  = ""
    var episode_number: Int  = -1
    var id: Int  = -1
    var name: String  = ""
    var overview: String  = ""
    var production_code: String  = ""
    var season_number: Int  = -1
    var still_path: String  = ""
    var vote_average: Double  = -1.0
    var vote_count: Int  = -1
}