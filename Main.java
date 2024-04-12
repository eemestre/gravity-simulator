class Main {

    public static void main(String[] args) {
        boolean isRunning = true;

        while(isRunning) {
            long old = System.currentTimeMillis();

            // ticks per second method
            while(true) {
                if(System.currentTimeMillis() - old >= 1000/tps) {
                    tick();
                    old = System.currentTimeMillis();
                }
                render();
            }
        }
    }

    public static void tick() {}
    public static void render() {}
}