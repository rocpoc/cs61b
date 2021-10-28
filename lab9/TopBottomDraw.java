class TopBottomDraw {

    /** An array of all cards in the deck. */
    public int[] deck;

    /** Given some deck, TopBottomDraw can find the
     *  best possible score for the starting player. Assume that our
     *  opponent is playing optimally to minimize our score.
     */
    public TopBottomDraw(int[] deck) {
        this.deck = deck;
    }

    /** Finds the best score, assuming our maximizer is going first.
     */
    public int findBestScore(int i, int j) {
        if (i == j) {
            return 0;
        } else if ((j - i) % 2 == deck.length % 2) {
            return Math.max(findBestScore(i + 1, j) + deck[i], findBestScore(i, j - 1) + deck[j]);
        } else {
            return Math.min(findBestScore(i + 1, j), findBestScore(i, j - 1));
        }
    }

    /** Test cases for TopBottomDraw.
     */
    public static void main(String[] args) {
        int[] exampleDeck1 = new int[] {1, 3, 45, 6, 7, 8, 9, 9};
        int[] exampleDeck2 = new int[] {1, 3, 45, 6, 7, 8, 9, 9, 2};
        TopBottomDraw tbp1 = new TopBottomDraw(exampleDeck1);
        TopBottomDraw tbp2 = new TopBottomDraw(exampleDeck2);
        System.out.printf("findBestScore returned %d, should be 63\n", tbp1.findBestScore(0, exampleDeck1.length - 1));
        System.out.printf("findBestScore returned %d, should be 27\n", tbp2.findBestScore(0, exampleDeck2.length - 1));
    }

}
