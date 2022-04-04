# jwt2

rutas:

    /employee
    /login
    /usuaris
    /hello


Usuari creat:

    {
    "username":"Oriol",
    "password":"password",
    "avatar":"http://imatge.com"
    }

    token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQ5MDkxMTk5LCJleHAiOjE2NDkxNzc1OTksImZ1bGxuYW1lIjoiT3Jpb2wiLCJyb2xlcyI6IlVTRVIifQ.vzRNdTMSMpUhmJjhf-BbFbLOXMfiazAWFBGN8K4-R_UnXY_8fPn14KaW6XC65zqjGFVdekzVHgpgLvUvB7zLbA


Usuari:
    
    @Id
    @GeneratedValue
    private Long id; //identificador autonumèric
    @Column(unique = true)
    private String username; //no es repeteix username
    private String password;
    private String avatar;
    private String rol = "USER"; //per defecte
    
UsuariDTO:

    private String username;
    private String avatar;
    private String rol;
    
    
Employee:
    
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Id
    @GeneratedValue
    private String nom;
    private String plataforma;
    private double preu;
    private int horesJugades;
