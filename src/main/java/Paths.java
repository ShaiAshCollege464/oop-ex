public enum Paths {
    auction("auction"), get_string("get-string"), check("check2");

    private String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath () {
        return "/fm1/" + this.path;
    }
}
