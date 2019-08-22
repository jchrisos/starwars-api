package com.starwars.dao

import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.QueryResult
import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.mysql.MySQLQueryResult
import com.github.jasync.sql.db.util.map
import com.starwars.models.Planet
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanetDAO {

    companion object {
        const val SQL_FIND_ALL = "SELECT * FROM planets"
        const val SQL_GET_BY_ID = "SELECT * FROM planets WHERE id = ?"
        const val SQL_FIND_BY_NAME = "SELECT * FROM planets WHERE name like ?"
        const val SQL_CREATE = "INSERT INTO planets (name, climate, terrain, appearance_films, create_date) value (?,?,?,?,?)"
        const val SQL_DELETE = "DELETE FROM planets WHERE id = ?"
    }

    @Inject
    lateinit var client: Connection

    fun findAll(): Observable<List<Planet>> {
        return Observable.fromFuture(
                client.sendPreparedStatement(SQL_FIND_ALL).map { queryResult ->
                    queryResult.rows.map { row ->
                        retrievePlanet(row)
                    }
                }
        )
    }

    fun getById(id: Long): Observable<Planet> {
        return Observable.fromFuture(
                client.sendPreparedStatement(SQL_GET_BY_ID, listOf(id)).map { queryResult ->
                    retrievePlanet(queryResult.rows[0])
                }
        )
    }

    fun findByName(q: String): Observable<List<Planet>> {
        return Observable.fromFuture(
                client.sendPreparedStatement(SQL_FIND_BY_NAME, listOf("%$q%")).map { queryResult ->
                    queryResult.rows.map { row ->
                        retrievePlanet(row)
                    }
                }
        )
    }

    fun create(planet: Planet): Observable<QueryResult> {
        return Observable.fromFuture(
                client.inTransaction {
                    it.sendPreparedStatement(SQL_CREATE, listOf(planet.name, planet.climate, planet.terrain, planet.appearanceFilms?:0, Date()), true)
                }
        )
    }

    fun delete(id: Long): Observable<QueryResult> {
        return Observable.fromFuture(
                client.inTransaction {
                    it.sendPreparedStatement(SQL_DELETE, listOf(id), true)
                }
        )
    }

    private fun retrievePlanet(row: RowData): Planet {
        return Planet(
                row.getLong("id"),
                row.getString("name")!!,
                row.getString("climate")!!,
                row.getString("terrain")!!,
                row.getInt("appearance_films"),
                row.getDate("create_date")?.toDate()
        )
    }

}