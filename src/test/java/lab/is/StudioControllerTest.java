package lab.is;

class StudioControllerTest extends SpringBootApplicationTest {
    protected String getEndpointGettingEntityById() {
        return "/api/v1/studios/{id}";
    }
}
