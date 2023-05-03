import java.util.Scanner
import kotlin.system.exitProcess

data class MenuItem(val menuItem:String, val menuAction: ()->Unit)

class Menu(private val archives: ArrayList<Archive>) {
    init {
        showMenu(generateMenuItemsForArchive())
    }

    private fun generateMenuItemsForArchive(): ArrayList<MenuItem> {
        val result: ArrayList<MenuItem> = ArrayList()
        result.add(MenuItem("0. Создать архив") { addArchive() })
        for (i in 0 until archives.size) {result.add(MenuItem("${i+1}. ${archives[i]}") { showMenu(generateMenuItemsForNotes(i)) })}
        result.add(MenuItem("${archives.size+1}. Выход") { exitArchiveMenu() })
        return result
    }

    private fun generateMenuItemsForNotes(selectedArchive: Int): ArrayList<MenuItem> {
        val result: ArrayList<MenuItem> = ArrayList()
        result.add(MenuItem("0. Создать заметку") { addNote(selectedArchive) })
        for (i in 0 until archives[selectedArchive].notes.size) {result.add(MenuItem("${i+1}. ${archives[selectedArchive].notes[i]}") {
            showNote(
                selectedArchive,
                i
            )
        })}
        result.add(MenuItem("${archives[selectedArchive].notes.size+1}. Выход") { exitNotesMenu() })
        return result
    }

    private fun showMenu(itemList: ArrayList<MenuItem>) {
        val scan = Scanner(System.`in`)
        var selectedItem: Int

        for (item in itemList) {
            println(item.menuItem)
        }

        print("Выберите пункт меню: ")
        do {
            if (scan.hasNext()) {
                if (scan.hasNextInt()) {
                    selectedItem = scan.nextInt()
                    when {
                        (selectedItem in 0..itemList.size) -> itemList[selectedItem].menuAction()
                        else -> showWrongInput()
                    }
                }
                else {
                    scan.next()
                    println("Надо ввести число")
                }
            }
        } while (true)
    }

    private fun addArchive() {
        println("Создание нового архива")
        println("Введите название архива:")
        val newItem = Scanner(System.`in`).nextLine()
        archives.add(Archive(newItem))
        showMenu(generateMenuItemsForArchive())
    }

    private fun addNote(archiveItem: Int) {
        println("Добавление заметки в архив ${archives[archiveItem]}")
        println("Введите название заметки:")
        val title = Scanner(System.`in`).nextLine()
        println("Введите текст заметки:")
        val body = Scanner(System.`in`).nextLine()
        archives[archiveItem].addNote(title, body)
        showMenu(generateMenuItemsForNotes(archiveItem))
    }

    private fun exitArchiveMenu() {
        println("Выход")
        exitProcess(0)
    }

    private fun showNote(archiveItem: Int, selectedItem: Int) {
        println("Текст заметки: ${archives[archiveItem].notes[selectedItem].body}")
        showMenu(generateMenuItemsForNotes(archiveItem))
    }

    private fun exitNotesMenu() {
        showMenu(generateMenuItemsForArchive())
    }

    private fun showWrongInput() {
        println("Нет такого пункта меню. Попробуйте еще раз")
    }
}