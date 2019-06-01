package un.eagle.elsa

import un.eagle.elsa.data.model.Notification
import un.eagle.elsa.data.model.Post

class MockData {
    companion object {
        fun notifications() : ArrayList<Notification> {
            val r = ArrayList<Notification>()
            r.add(Notification("Michael Jackson", Notification.FOLLOW))
            r.add(Notification("Justin Bieber", Notification.SHARE))
            r.add(Notification("El Chavo", Notification.SHARE))
            r.add(Notification("Victor Ramirez", Notification.FOLLOW))
            r.add(Notification("Juan Valdez", Notification.FOLLOW))
            r.add(Notification("Nicolas Larra", Notification.SHARE))
            r.add(Notification("Juan Camilo", Notification.FOLLOW))
            r.add(Notification("Diego Niquefa", Notification.SHARE))
            return r
        }


        fun userList() : ArrayList<String> {
            val list = ArrayList<String>()
            list.addAll(arrayOf("Diego Niquefas", "Juan Moreno", "Laura Santos", "Christian Sanabria", "Sebastian Chaves"))
            list.addAll(arrayOf("Victor Ramirez", "Diego Caballero", "Alan Navarro", "Osman Jimenez", "Manuel Vergara"))
            list.addAll(arrayOf("Yoni el kpo", "Don Alberto", "Doña Belén", "Jose Niquefa", "Rafael Niquefa"))
            return list
        }

        fun posts() : ArrayList<Post> {
            val posts = ArrayList<Post>()
            posts.add(Post("Tomi Tomi", "Vamo a jugar, vamo a jugar, vamo a jugar"))
            posts.add(Post("Diego", "Arki too hard :'v"))
            posts.add(Post("Mater Chris", "zolo rails loka"))
            posts.add(Post("Laura", "rapunzel es mi princesa favorita"))
            posts.add(Post("Pambele", "Es mejor ser rico que ser pobre", "Juan Chaves"))
            posts.add(Post("Juan", "Moana to mama"))
            posts.add(Post("Chaves", "soy 100tifiko en gugol clau"))
            posts.add(Post("Juan Diego", "Finde de rumba", "Master Chris"))
            posts.add(Post("Chili", "Chillin"))
            posts.add(Post("Diego", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."))
            return posts
        }

    }
}