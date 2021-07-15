package com.example.agenda.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.agenda.database.DAO.RoomAlunoDAO
import com.example.agenda.database.DAO.TelefoneDAO
import com.example.agenda.database.converter.Converters
import com.example.agenda.model.Aluno
import com.example.agenda.model.Telefone
import com.example.agenda.model.TipoTelefone

@Database(entities = [Aluno::class, Telefone::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AgendaDatabase : RoomDatabase(){

    abstract fun getRoomAlunoDAO(): RoomAlunoDAO
    abstract fun getTelefoneDAO(): TelefoneDAO

    companion object {
        private lateinit var instanceDB: AgendaDatabase

        fun getInstance(context: Context): AgendaDatabase {
            if(!Companion::instanceDB.isInitialized){
                synchronized(AgendaDatabase::class){
                    instanceDB = Room.databaseBuilder(
                        context,
                        AgendaDatabase::class.java,
                        "agenda.db"
                    )
                        .addMigrations(object : Migration(1, 2) {
                            override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("ALTER TABLE 'aluno' ADD COLUMN 'sobrenome' TEXT NOT NULL DEFAULT '' ")
                            }
                        }, object : Migration(2, 3) {
                            override fun migrate(database: SupportSQLiteDatabase) {
                                //Criar nova tabela com as informações desejadas
                                database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `telefone` TEXT NOT NULL, `email` TEXT NOT NULL)")
                                // Copiar dados da tabela antiga para nova
                                database.execSQL("INSERT INTO Aluno_novo (id,nome,telefone,email) SELECT id,nome,telefone,email FROM Aluno")
                                // Remove tabela antiga
                                database.execSQL("DROP TABLE Aluno")
                                // Renomear a tabela nova com o nome da antiga
                                database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno")
                            }
                        }, object : Migration(3, 4) {
                            override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("ALTER TABLE 'Aluno' ADD COLUMN 'momentoDeCadastro' INTEGER")
                            }
                        }, object : Migration(4, 5) {
                            override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_Novo` (`momentoDeCadastro` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `telefoneFixo` TEXT NOT NULL, `telefoneCelular` TEXT NOT NULL default '', `email` TEXT NOT NULL)")

                                database.execSQL("INSERT INTO Aluno_novo (id,nome,telefoneFixo,momentoDeCadastro,email) SELECT id,nome,telefone,momentoDeCadastro,email FROM Aluno")

                                database.execSQL("DROP TABLE Aluno")

                                database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno")
                            }

                        },
                            object : Migration(5, 6) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    val tipo = TipoTelefone.FIXO.name
                                    database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (`momentoDeCadastro` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `email` TEXT NOT NULL)")
                                    database.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `numero` TEXT NOT NULL, `tipo` TEXT NOT NULL DEFAULT $tipo , `alunoId` INTEGER NOT NULL, FOREIGN KEY(`alunoId`) REFERENCES `Aluno`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")

                                    database.execSQL("INSERT INTO Aluno_novo (id,nome,momentoDeCadastro,email) SELECT id,nome,momentoDeCadastro,email FROM Aluno")
                                    database.execSQL("INSERT INTO Telefone (alunoId, numero) SELECT id,telefoneFixo FROM Aluno")

                                    database.execSQL("DROP TABLE Aluno")

                                    database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno")
                                }

                            }, object: Migration(6,7) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    database.execSQL("UPDATE Telefone SET tipo = ?", arrayOf(TipoTelefone.FIXO))
                                }

                            }, object : Migration(7,8) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    database.execSQL("UPDATE Telefone SET tipo = ?", arrayOf(TipoTelefone.FIXO.name))
                                }
                            })
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instanceDB
        }
    }

}