package utils;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data

public class ClientData{
    private final String login;
    private final String password;
    private final String status;
}

