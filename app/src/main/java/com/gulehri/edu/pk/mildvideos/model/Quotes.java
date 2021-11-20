package com.gulehri.edu.pk.mildvideos.model;

/**
 * Created by Shahid Iqbal on 14,November,2021
 */
public class Quotes {
    private final String quote, aurthur;

    public Quotes(String quote, String aurthur) {
        this.quote = quote;
        this.aurthur = aurthur;
    }

    public static final Quotes[] QUOTES = {
            new Quotes("Almost everything will work again if you unplug it for a few minutes, including you", "Anne Lamott"),
            new Quotes("Adopting the right attitude can convert a negative stress into a positive one.", "Hans Selye"),
            new Quotes("Sometimes the most productive thing you can do is relax.", "Mark Black"),
            new Quotes("Stop acting so small. You are the universe in ecstatic motion.", "Rumi"),
            new Quotes("What you seek is seeking you.", "Rumi"),
            new Quotes("Don’t grieve. Anything you lose comes round in another form.", "Rumi"),
            new Quotes("You were born with wings, why prefer to crawl through life?", "Rumi"),
            new Quotes("Lovers don't finally meet somewhere. They're in each other all along.", "Rumi"),
            new Quotes("Everything in the universe is within you. Ask all from yourself.", "Rumi"),
            new Quotes("Let yourself be drawn by the stronger pull of that which you truly love.", "Rumi"),
            new Quotes("Be grateful for whoever comes, because each has been sent as a guide from beyond.", "Rumi"),
            new Quotes("We carry inside us the wonders we seek outside us.", "Rumi"),
            new Quotes("Only from the heart can you touch the sky.", "Rumi"),
            new Quotes("You don’t have to control your thoughts.You just have to stop letting them control you.", "Dan Millman"),
            new Quotes("Rule number one is, don’t sweat the small stuff. Rule number two is, it’s all small stuff.", "Robert Eliot"),
            new Quotes("Within you, there is a stillness and a sanctuary to which you can retreat at any time and be yourself.", "Hermann Hesse"),
            new Quotes("Feelings come and go like clouds in a windy sky. Conscious breathing is my anchor.", "Thich Hnah"),
            new Quotes("The garden of the world has no limit except in your mind", "Rumi"),
            new Quotes("The time to relax is when you don’t have time for it.", "Sydney J. Harris"),
            new Quotes("The greatest weapon against stress is our ability to choose one thought over another.", "William James"),
            new Quotes("If you treat every situation as a life and death matter, you’ll die a lot of times.", "Dean Smith"),
            new Quotes("The most excellent jihad is the conquest of one’s self.", "Muhammad (SAW)"),
            new Quotes("This world is a prison for the believers and a paradise for the non-believers.", "Muhammad (SAW)"),
            new Quotes("Kindness is a mark of faith, and whoever has not kindness has not faith.", "Muhammad (SAW)"),
            new Quotes("Who is the most favored of God? He from whom the greatest good comes to His creature.", "Muhammad (SAW)"),
            new Quotes("Happy is the man who avoids hardship, but how fine is the man who is afflicted and shows endurance.", "Muhammad (SAW)"),
            new Quotes("The best richness is the richness of the soul.", "Muhammad (SAW)"),
            new Quotes("Tell your heart that the fear of suffering is worse than the suffering itself.", "Paulo Coelho"),
            new Quotes("Stress is an important dragon to slay–or at least tame–in your life.", "Marilu Henner"),
            new Quotes("A year from now, everything you’re stressing about won’t even matter.", "Anon"),
    };

    public String getQuote() {
        return quote;
    }

    public String getAurthur() {
        return aurthur;
    }
}
