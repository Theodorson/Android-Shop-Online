package com.example.shop_online.database;

import com.example.shop_online.book.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseBook {

    private DatabaseReference databaseRef;

    public DatabaseBook(){}

    public void populateDatabase(){

        Book book1 = new Book(
                "Attack On Titan 1", "Hajime Isayama", "Kodansha America, Inc", "English",
                "19 Jun 2012", "Several hundred years ago, humans were nearly exterminated by giants. Giants are typically several stories tall, seem to have no intelligence and who devour human beings. A small percentage of humanity survied barricading themselves in a city protected by walls even taller than the biggest of giants. Flash forward to the present and the city has not seen a giant in over 100 years - before teenager Eren and his foster sister Mikasa witness something horrific as the city walls are destroyed by a super-giant that appears from nowhere.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/6126/9781612620244.jpg", 200, 55.9f
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
                "18 Jun 2015", "Ghouls live among us, the same as normal people in every way - except their craving for human flesh. Ken Kaneki is an ordinary college student until a violent encounter turns him into the first half-human half-ghoul hybrid. Trapped between two worlds, he must survive Ghoul turf wars, learn more about Ghoul society and master his new powers.\n" +
                "\n" +
                "Shy Ken Kaneki is thrilled to go on a date with the beautiful Rize. But it turns out that she's only interested in his body - eating it, that is. When a morally questionable rescue transforms him into the first half-human half-Ghoul hybrid, Ken is drawn into the dark and violent world of Ghouls, which exists alongside our own.",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421580364.jpg", 224 , 30
        );
        Book book9 = new Book(
                "My Hero Academia, Vol. 4", "Kohei Horikoshi", "Viz Media, Subs. of Shogakukan Inc", "English",
                " 19 May 2016", "What would the world be like if 80 percent of the population manifested superpowers called \"Quirks\"? Heroes and villains would be battling it out everywhere! Being a hero would mean learning to use your power, but where would you go to study? The Hero Academy of course! But what would you do if you were one of the 20 percent who were born Quirkless?\n" +
                "The U.A. High sports festival is a chance for the budding heroes to show their stuff and find a superhero mentor. The students have already struggled through a grueling preliminary round, but now they have to team up to prove they're capable of moving on to the next stage. The whole country is watching, and so are the shadowy forces that attacked the academy...",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421585116.jpg", 192, 32.5f
        );
        Book book10 = new Book(
                "One-Punch Man, Vol. 1", "One", "Viz Media, Subs. of Shogakukan Inc", "English",
                " 24 Sep 2015", "Nothing about Saitama passes the eyeball test when it comes to superheroes, from his lifeless expression to his bald head to his unimpressive physique. However, this average-looking guy has a not-so-average problem - he just can't seem to find an opponent strong enough to take on! Every time a promising villain appears, he beats the snot out of 'em with one punch! Can Saitama finally find an opponent who can go toe-to-toe with him and give his life some meaning? Or is he doomed to a life of superpowered boredom?",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421585642.jpg", 200, 28
        );
        Book book11 = new Book(
                "Berserk Deluxe Volume 1", "Kentaro Miura", "Dark Horse Comics,U.S.", "English",
                "25 Apr 2019", "Have you got the Guts? Kentaro Miura's Berserk has outraged, horrified, and delighted manga and anime fanatics since 1989, creating an international legion of hardcore devotees and inspiring a plethora of TV series, feature films, and video games. And now the badass champion of adult fantasy manga is presented in an oversized 7 x 10 deluxe hardcover edition, nearly 700 pages amassing the first three Berserk volumes, with following volumes to come to serve up the entire series in handsome bookshelf collections. No Guts, no glory!",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/5067/9781506711980.jpg", 696, 75.9f
        );
        Book book12 = new Book(
                "Naruto, Vol. 1", "Masashi Kishimoto ", "Viz Media, Subs. of Shogakukan Inc", "English",
                " 03 Sep 2007", "Naruto is a ninja-in-training with a need for attention, a knack for mischief, and sealed within him, a strange, formidable power. His antics amuse his instructor Kakashi and irritate his teammates, intense Sasuki and witty Sakura, but Naruto is serious about becoming the greatest ninja in the village of Konohagakure!\n" +
                "\n" +
                "In another world, ninja are the ultimate power - and in the village of Konohagakure live the stealthiest ninja in the world. But twelve years ago Konohagakure was attacked by a fearsome threat - a nine-tailed fox demon which claims the life of the Hokage, the village champion. Today, peace has returned, and a troublemaking orphan named Uzumaki Naruto is struggling to graduate from the Ninja Academy. His goal: to become the next Hokage. But unknown to Naruto and his classmates, within him is a terrifying force...",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/5693/9781569319000.jpg", 192, 25.0f
        );
        Book book13 = new Book(
                "Hunter x Hunter, Vol. 34", "Yoshihiro Togashi ", "Viz Media, Subs. of Shogakukan Inc", "English",
                "22 Mar 2018", "Plucky Gon's quest to find his dad leads him into a whole world of crazy adventure.\n" +
                "\n" +
                "Hunters are a special breed, dedicated to tracking down treasures, magical beasts, and even other people. But such pursuits require a license, and less than one in a hundred thousand can pass the grueling qualification exam. Those who do pass gain access to restricted areas, amazing stores of information, and the right to call themselves Hunters.\n" +
                "\n" +
                "All aboard the Black Whale, because the journey to the Dark Continent is beginning! And this will be anything but a pleasure cruise, because the King of Kakin has ordered his princes to fight to the death to succeed him-on the ship! But this won't be an ordinary battle. They'll be using hired guards with fantastic powers to fight! Including Kurapika and some of the Zodiac members! ",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421599489.jpg", 208, 45.5f
        );
        Book book14 = new Book(
                "Fullmetal Alchemist (3-in-1 Edition)", "Hiromu Arakawa", "Viz Media, Subs. of Shogakukan Inc", "English",
                "23 Jun 2011", "Contains volumes 1, 2 and 3 of Fullmetal Alchemist. Alchemy: the mystical power to alter the natural world, somewhere between magic art, and science. When two brothers, Edward and Alphonse Elric, dabbled in these powers to grant their dearest wish, one of them lost an arm and leg...and the other became nothing but a soul locked into a body of living iron. Now they are agents of the government, slaves of the military -alchemical complex, using their unique powers to obey their orders...even to kill. But their powers aren't unique. The world crawls with evil alchemists. And in pursuit of the ultimate alchemical treasure, the Philsopher's Stone, their enemies are even more ruthless than they are...",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421540184.jpg", 576, 80.0f
        );
        Book book15 = new Book(
                "Bleach, Vol. 25", "Tite Kubo ", "Viz Media, Subs. of Shogakukan Inc", "English",
                "05 Jan 2009", "Part-time student, full-time Soul Reaper, Ichigo is one of the chosen few guardians of the afterlife. Ichigo Kurosaki never asked for the ability to see ghosts--he was born with the gift. When his family is attacked by a Hollow--a malevolent lost soul--Ichigo becomes a Soul Reaper, dedicating his life to protecting the innocent and helping the tortured spirits themselves find peace. Find out why Tite Kubo's Bleach has become an international manga smash-hit! Ichigo's recent battles with the Arrancars have proven that if he wants to protect his friends he must get stronger, and the only way to do that is to control his inner Hollow. Ichigo turns to the Visoreds, ex-Soul Reapers who have been Hollowfied, to teach him. But before his training begins, Ichigo must do battle against his Hollow self--winner takes his soul!",
                "https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4215/9781421517964.jpg", 200, 35.9f
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
        databaseRef.push().setValue(book11);
        databaseRef.push().setValue(book12);
        databaseRef.push().setValue(book13);
        databaseRef.push().setValue(book14);
        databaseRef.push().setValue(book15);


    }

}
