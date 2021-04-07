package com.example.shop_online;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DatabaseBook {

    private DatabaseReference databaseRef;

    public DatabaseBook(){}

    public void populateDatabase(){

        Book book1 = new Book(
                "Attack On Titan 1", "Hajime Isayama", "Kodansha America, Inc", "English",
                "19 Jun 2012", "Several hundred years ago, humans were nearly exterminated by giants. Giants are typically several stories tall, seem to have no intelligence and who devour human beings. A small percentage of humanity survied barricading themselves in a city protected by walls even taller than the biggest of giants. Flash forward to the present and the city has not seen a giant in over 100 years - before teenager Eren and his foster sister Mikasa witness something horrific as the city walls are destroyed by a super-giant that appears from nowhere.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/6126/9781612620244.jpg", 200, 30
                );
        Book book2 = new Book(
                "Jujutsu Kaisen 1", "Gege Akutami", "NORMA EDITORIAL, S.A.", "Spain",
                "16 Mar 2020", "Manga style and shonen (youth-oriented) paperback bound comic book of 192 black and white inner pages plus covers and dust jackets with an oriental reading sense. JUJUTSU KAISEN ARRIVES, THE SHONEN THAT IS CAUSING FUROR IN JAPAN ",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9788/4679/9788467941920.jpg", 192, 25
        );
        Book book3 = new Book(
                "Six of Crows: Collector's Edition : Book 1", "Leigh Bardugo", "Hachette Children's Group", "English",
                "11 Oct 2018", "A glorious Collector's Edition of New York Times bestselling, epic fantasy novel, SIX OF CROWS. Beautifully designed, with an exclusive letter from the author and six stunning full-colour character portraits. This covetable hardback with red sprayed edges is a perfect gift for fans, and a perfect way to discover the unforgettable writing of Leigh Bardugo.\n" +
                "\n" +
                "Criminal prodigy Kaz Brekker is offered a chance at a deadly heist: break into the Ice Court - a military stronghold that has never been breached - and retrieve a hostage whose knowledge could change Grisha magic forever. To succeed would mean riches beyond his wildest dreams - but he can't pull it off alone . . .",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/5101/9781510106284.jpg", 512, 50
        );
        Book book4 = new Book(
                "The Walking Dead Compendium Volume 4", "Robert Kirkman", "Image Comics", "English",
                "08 Oct 2019", "From the Whisperers to the\n" +
                "Commonwealth, Rick Grimes meets new allies and enemies to the way to reclaiming\n" +
                "the world from the dead. Wars are started, and dear friends fall... Collects THE\n" +
                "WALKING DEAD #145-193.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/5343/9781534313408.jpg", 1096, 100
        );
        Book book5 = new Book(
                "The Seven Deadly Sins 1", "Nakaba Suzuki", "Kodansha America, Inc", "English",
                "18 May 2016", "This new manga series takes readers to the land of Britannia, a picturesque country ruled by the benevolent King Lyonnesse - or at least it was, until the king's guard assassinated him and started a full-blown Holy War! Now the king's only daughter Elizabeth must seek the aid of the dreaded warriors, the Seven Deadly Sins. Wrongly framed and sent into exile, they're now the princess's only hope to free the kingdom from the grip of the villainous Holy Knights!",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/6126/9781612629216.jpg", 192, 30
        );
        Book book6 = new Book(
                "Parasyte 1", "Hitoshi Iwaaki", "Kodansha America, Inc", "English",
                "26 Jul 2011", "An ordinary teenage boy named Shinichi Izumilives with his parents in a quiet neighborhood until alien parasites invade. They are worm-like creatures that enter human 'hosts' through their ears or noses, and take over their brains. A Parasite attempts to crawl into Shinichi's ear and take over his brain while he sleeps, but is stopped by his headphones and accidentally burrows into one of his hands instead. With separate intellects, he and his parasite, Migi, fight the invasion of the town.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/6126/9781612620732.jpg", 282, 35
        );
        Book book7 = new Book(
                "Civil War", "Mark Millar", "Panini Publishing Ltd", "English",
                "26 Jul 2018", "When a battle between a group of Super Villains and a team of young heroes goes horribly wrong, resulting in a large number of civilian casualties, events are set in motion that will tear the Marvel Universe's Super Hero community apart. Loyalties are tested and friendships torn asunder as each hero is forced to chose who's side they're on.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/9052/9781905239603.jpg", 196, 50
        );
        Book book8 = new Book(
                "Tokyo Ghoul, Vol. 1", "Sui Ishida", "Viz Media, Subs. of Shogakukan Inc", "English",
                "19 Jun 2012", "Several hundred years ago, humans were nearly exterminated by giants. Giants are typically several stories tall, seem to have no intelligence and who devour human beings. A small percentage of humanity survied barricading themselves in a city protected by walls even taller than the biggest of giants. Flash forward to the present and the city has not seen a giant in over 100 years - before teenager Elen and his foster sister Mikasa witness something horrific as the city walls are destroyed by a super-giant that appears from nowhere.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/6126/9781612620244.jpg", 200, 30
        );
        Book book9 = new Book(
                "Tokyo Ghoul, Vol. 1", "Sui Ishida", "", "English",
                "18 Jun 2015", "Ghouls live among us, the same as normal people in every way - except their craving for human flesh. Ken Kaneki is an ordinary college student until a violent encounter turns him into the first half-human half-ghoul hybrid. Trapped between two worlds, he must survive Ghoul turf wars, learn more about Ghoul society and master his new powers.\n" +
                "\n" +
                "Shy Ken Kaneki is thrilled to go on a date with the beautiful Rize. But it turns out that she's only interested in his body - eating it, that is. When a morally questionable rescue transforms him into the first half-human half-Ghoul hybrid, Ken is drawn into the dark and violent world of Ghouls, which exists alongside our own.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421580364.jpg", 224, 25
        );
        Book book10 = new Book(
                "One-Punch Man, Vol. 1", "One", "Viz Media, Subs. of Shogakukan Inc", "English",
                " 24 Sep 2015", "Nothing about Saitama passes the eyeball test when it comes to superheroes, from his lifeless expression to his bald head to his unimpressive physique. However, this average-looking guy has a not-so-average problem - he just can't seem to find an opponent strong enough to take on! Every time a promising villain appears, he beats the snot out of 'em with one punch! Can Saitama finally find an opponent who can go toe-to-toe with him and give his life some meaning? Or is he doomed to a life of superpowered boredom?",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421585642.jpg", 200, 28
        );

        databaseRef = FirebaseDatabase.getInstance().getReference().child("books");
        databaseRef.push().setValue(book1);
        databaseRef.push().setValue(book2);
        databaseRef.push().setValue(book3);
        databaseRef.push().setValue(book4);
        databaseRef.push().setValue(book5);
        databaseRef.push().setValue(book6);
        databaseRef.push().setValue(book7);
        databaseRef.push().setValue(book8);
        databaseRef.push().setValue(book9);
        databaseRef.push().setValue(book10);


    }

}
