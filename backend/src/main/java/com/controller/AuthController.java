@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepo.findByLogin(request.getLogin()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login already exists");
        }

        User user = User.builder()
                .login(request.getLogin())
                .password(encoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();

        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

        String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
