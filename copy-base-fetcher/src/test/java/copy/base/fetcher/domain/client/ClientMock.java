package copy.base.fetcher.domain.client;

public class ClientMock {
    private static long MOCKED_CLIENT_ID = 1L;
    private static String MOCKED_CLIENT_FIRST_NAME = "John";
    private static String MOCKED_CLIENT_LAST_NAME = "Smith";
    private static String MOCKED_CLIENT_EMAIL = "johnsmith@gmail.com";
    private static String MOCKED_CLIENT_PHONE = "555218475";

    public static final Client MOCKED_CLIENT = new Client(
            MOCKED_CLIENT_ID,
            MOCKED_CLIENT_FIRST_NAME,
            MOCKED_CLIENT_LAST_NAME,
            MOCKED_CLIENT_EMAIL,
            MOCKED_CLIENT_PHONE);
}
