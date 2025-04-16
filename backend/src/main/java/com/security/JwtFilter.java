@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepo.findByLogin(username).orElse(null);
            if (user != null && jwtUtil.validateToken(jwt, new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(), List.of()))) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user.getLogin(), null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
