import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projet_kotlin_ap5.entities.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musics: List<SongEntity>)

    @Query("SELECT * FROM song")
    suspend fun getAllMusics(): List<SongEntity>

    @Query("DELETE FROM song")
    suspend fun deleteAll()
}
