@Entity
@Table(name = "moderation_actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModerationAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionType; // e.g., "REMOVE_COMMENT", "BAN_USER"

    private String moderatorUsername;

    private String targetUsername;

    private Long targetCommentId; // nullable

    private Long targetThreadId;  // nullable

    private LocalDateTime timestamp;

    private String reason;
}
