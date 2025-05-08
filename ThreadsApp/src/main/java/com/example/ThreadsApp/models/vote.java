@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private boolean isUpvote;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private ThreadPost thread;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
