package ru.rutmiit;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rutmiit.dto.AddProductDto;
import ru.rutmiit.dto.AddArticleDto;
import ru.rutmiit.dto.ProductPropertiesDto;
import ru.rutmiit.dto.TextArticleDto;
import ru.rutmiit.models.entities.Role;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.enums.*;
import ru.rutmiit.repositories.UserRepository;
import ru.rutmiit.repositories.UserRoleRepository;
import ru.rutmiit.services.ArticleService;
import ru.rutmiit.services.ProductService;

import java.util.List;

@Slf4j
@Component
public class Init implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;
    private final ProductService productService;
    private final ArticleService articleService;

    public Init(UserRepository userRepository,
                UserRoleRepository userRoleRepository,
                PasswordEncoder passwordEncoder,
                @Value("${app.default.password}") String defaultPassword,
                ProductService productService,
                ArticleService articleService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
        this.productService = productService;
        this.articleService = articleService;
        log.info("Init компонент инициализирован");
    }

    @Override
    public void run(String... args) {
        log.info("Запуск инициализации начальных данных");
        initRoles();
        initUsers();
        initProducts();
        initArticles();
        log.info("Инициализация начальных данных завершена");
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            log.info("Создание ролей");

            Role userRole = new Role(UserRoles.USER);
            Role moderatorRole = new Role(UserRoles.MODERATOR);
            Role adminRole = new Role(UserRoles.ADMIN);

            userRoleRepository.saveAll(List.of(userRole, moderatorRole, adminRole));
            log.info("Роли созданы: USER, MODERATOR, ADMIN");
        } else {
            log.info("Роли уже существуют");
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            log.info("Создание тестовых пользователей");
            initAdmin();
            initModerator();
            initNormalUser();
            log.info("Тестовые пользователи созданы");
        } else {
            log.info("Пользователи уже существуют");
        }
    }

    private void initAdmin() {
        var adminRole = userRoleRepository
                .findRoleByName(UserRoles.ADMIN)
                .orElseThrow();
        var moderatorRole = userRoleRepository
                .findRoleByName(UserRoles.MODERATOR)
                .orElseThrow();
        var userRole = userRoleRepository
                .findRoleByName(UserRoles.USER)
                .orElseThrow();

        User admin = new User(
                "admin",
                "admin@example.com",
                "Admin Address",
                passwordEncoder.encode(defaultPassword)
        );

        admin.setRoles(List.of(adminRole, moderatorRole, userRole));

        userRepository.save(admin);
        log.info("Создан администратор: admin");
    }

    private void initModerator() {
        var moderatorRole = userRoleRepository
                .findRoleByName(UserRoles.MODERATOR)
                .orElseThrow();
        var userRole = userRoleRepository
                .findRoleByName(UserRoles.USER)
                .orElseThrow();

        User moderator = new User(
                "moderator",
                "moderator@example.com",
                "Moderator Address",
                passwordEncoder.encode(defaultPassword)
        );
        moderator.setRoles(List.of(moderatorRole, userRole));

        userRepository.save(moderator);
        log.info("Создан модератор: moderator");
    }

    private void initNormalUser() {
        var userRole = userRoleRepository
                .findRoleByName(UserRoles.USER)
                .orElseThrow();

        User user = new User(
                "user",
                "user@example.com",
                "User Address",
                passwordEncoder.encode(defaultPassword)
        );
        user.setRoles(List.of(userRole));

        userRepository.save(user);
        log.info("Создан обычный пользователь: user");
    }

    private void initProducts() {
        if (productService.allProducts().size() > 0) {
            log.info("Товары уже существуют, пропускаем инициализацию");
            return;
        }

        log.info("Создание товаров");

        AddProductDto roseDto = new AddProductDto();
        roseDto.setName("Роза");
        roseDto.setPrice(1500.00);
        roseDto.setDescription("Красивая красная роза с приятным ароматом. Требует регулярного полива и солнечного света.");

        ProductPropertiesDto roseProps = new ProductPropertiesDto();
        roseProps.setCareLevel(CareLevel.COMPLEX);
        roseProps.setLightRequirement(LightRequirement.MEDIUM);
        roseProps.setWateringFrequency(WateringFrequency.FREQUENT);
        roseProps.setGrowthRate(GrowthRate.MODERATE);
        roseProps.setSizePlant(SizePlant.MEDIUM);
        roseProps.setPetSafe(false);
        roseDto.setProperties(roseProps);


        productService.addProduct(roseDto);
        log.info("Создан товар: Роза");


        AddProductDto cactusDto = new AddProductDto();
        cactusDto.setName("Кактус");
        cactusDto.setPrice(800.00);
        cactusDto.setDescription("Неприхотливое растение, идеально подходит для занятых людей. Требует минимального ухода.");

        ProductPropertiesDto cactusProps = new ProductPropertiesDto();
        cactusProps.setCareLevel(CareLevel.SIMPLE);
        cactusProps.setLightRequirement(LightRequirement.HIGH);
        cactusProps.setWateringFrequency(WateringFrequency.RARE);
        cactusProps.setGrowthRate(GrowthRate.SLOW);
        cactusProps.setSizePlant(SizePlant.SMALL);
        cactusProps.setPetSafe(false);
        cactusDto.setProperties(cactusProps);

        productService.addProduct(cactusDto);
        log.info("Создан товар: Кактус");


        AddProductDto violetDto = new AddProductDto();
        violetDto.setName("Фиалка");
        violetDto.setPrice(900.00);
        violetDto.setDescription("Нежное комнатное растение с красивыми цветами. Любит рассеянный свет и умеренный полив.");

        ProductPropertiesDto violetProps = new ProductPropertiesDto();
        violetProps.setCareLevel(CareLevel.MEDIUM);
        violetProps.setLightRequirement(LightRequirement.MEDIUM);
        violetProps.setWateringFrequency(WateringFrequency.MODERATE);
        violetProps.setGrowthRate(GrowthRate.MODERATE);
        violetProps.setSizePlant(SizePlant.SMALL);
        violetProps.setPetSafe(true);
        violetDto.setProperties(violetProps);

        productService.addProduct(violetDto);
        log.info("Создан товар: Фиалка");


        AddProductDto monsteraDto = new AddProductDto();
        monsteraDto.setName("Монстера");
        monsteraDto.setPrice(2500.00);
        monsteraDto.setDescription("Крупное декоративное растение с резными листьями. Отлично очищает воздух.");

        ProductPropertiesDto monsteraProps = new ProductPropertiesDto();
        monsteraProps.setCareLevel(CareLevel.SIMPLE);
        monsteraProps.setLightRequirement(LightRequirement.HIGH);
        monsteraProps.setWateringFrequency(WateringFrequency.MODERATE);
        monsteraProps.setGrowthRate(GrowthRate.FAST);
        monsteraProps.setSizePlant(SizePlant.FLOOR);
        monsteraProps.setPetSafe(false);
        monsteraDto.setProperties(monsteraProps);

        productService.addProduct(monsteraDto);
        log.info("Создан товар: Монстера");


        AddProductDto sansevieriaDto = new AddProductDto();
        sansevieriaDto.setName("Сансевиерия");
        sansevieriaDto.setPrice(1200.00);
        sansevieriaDto.setDescription("Очень выносливое растение, прощает забывчивых хозяев. Очищает воздух от токсинов.");

        ProductPropertiesDto sansevieriaProps = new ProductPropertiesDto();
        sansevieriaProps.setCareLevel(CareLevel.SIMPLE);
        sansevieriaProps.setLightRequirement(LightRequirement.MEDIUM);
        sansevieriaProps.setWateringFrequency(WateringFrequency.RARE);
        sansevieriaProps.setGrowthRate(GrowthRate.SLOW);
        sansevieriaProps.setSizePlant(SizePlant.MEDIUM);
        sansevieriaProps.setPetSafe(false);
        sansevieriaDto.setProperties(sansevieriaProps);

        productService.addProduct(sansevieriaDto);
        log.info("Создан товар: Сансевиерия");

        log.info("Создано 5 тестовых товаров");
    }

    private void initArticles() {
        if (articleService.allArticles().size() > 0) {
            log.info("Статьи уже существуют, пропускаем инициализацию");
            return;
        }

        log.info("Создание тестовых статей");

        AddArticleDto roseArticleDto = new AddArticleDto();
        roseArticleDto.setTitle("Как правильно ухаживать за розами");
        roseArticleDto.setPlantFamily(PlantFamily.DECORATIVE_FLOWERING);
        roseArticleDto.setProductName("Роза");

        TextArticleDto roseTextDto = new TextArticleDto();
        roseTextDto.setCare("Розы требуют регулярного ухода. Полив должен быть обильным, но не частым - примерно 1-2 раза в неделю в зависимости от погоды. " +
                "Важно поливать розы под корень, избегая попадания воды на листья, чтобы предотвратить грибковые заболевания. " +
                "Ранней весной необходимо проводить обрезку, удаляя старые, больные и слабые побеги. " +
                "Летом важно регулярно удалять увядшие цветы - это стимулирует повторное цветение. " +
                "На зиму розы нужно укрывать, особенно в регионах с суровыми зимами. " +
                "Подкормки проводятся несколько раз за сезон: весной - азотными удобрениями, летом - комплексными, осенью - калийно-фосфорными. " +
                "Обязательно рыхлите почву вокруг кустов и мульчируйте ее для сохранения влаги. " +
                "При появлении признаков заболеваний обработайте розы специальными препаратами.");

        roseTextDto.setReplication("Розы размножаются несколькими способами. Самый популярный метод - черенкование. " +
                "Для этого нарезают полуодревесневшие черенки длиной 15-20 см с 3-4 почками. Нижний срез делают косым, верхний - прямым. " +
                "Черенки высаживают в рыхлый питательный грунт, заглубляя до середины. Накрывают банкой или пленкой для создания парникового эффекта. " +
                "Укоренение происходит через 3-4 недели. Также розы можно размножать отводками - пригибая побег к земле и присыпая его почвой. " +
                "Редко используется семенное размножение, так как оно не сохраняет сортовые признаки. " +
                "Прививка - еще один способ, который используют для получения штамбовых роз или улучшения свойств корневой системы. " +
                "Лучшее время для размножения - весна или начало лета.");

        roseTextDto.setIllness("Розы подвержены различным заболеваниям. Самое распространенное - мучнистая роса, проявляющаяся белым налетом на листьях. " +
                "Для профилактики избегайте загущенных посадок и обеспечьте хорошую циркуляцию воздуха. При появлении признаков используйте фунгициды. " +
                "Черная пятнистость - еще одно грибковое заболевание, при котором на листьях появляются темные пятна. " +
                "Пораженные листья нужно удалить и сжечь, а растение обработать медьсодержащими препаратами. " +
                "Ржавчина роз проявляется оранжевыми подушечками на нижней стороне листьев. Проводите профилактические обработки весной. " +
                "Серая гниль поражает бутоны и молодые побеги. Избегайте переувлажнения и загущенных посадок. " +
                "Бактериальный рак вызывает образование наростов на корнях. Больные растения уничтожают.");

        roseTextDto.setPests("На розах часто паразитирует тля - мелкие насекомые, высасывающие сок из молодых побегов. " +
                "Бороться с тлей можно мыльным раствором или инсектицидами. Паутинный клещ появляется в жаркую сухую погоду, оплетая листья тонкой паутиной. " +
                "Повышайте влажность воздуха и обрабатывайте акарицидами. Розанный пилильщик - его личинки выедают ходы внутри побегов. " +
                "Пораженные побеги нужно обрезать и сжигать. Трипсы повреждают бутоны и цветы, вызывая их деформацию. " +
                "Против них эффективны системные инсектициды. Щитовки и ложнощитовки прикрепляются к стеблям и питаются соком. " +
                "Их можно удалить механически или обработать масляными эмульсиями. Проводите профилактические обработки весной до распускания почек.");

        roseArticleDto.setTextArticle(roseTextDto);
        articleService.addArticle(roseArticleDto);
        log.info("Создана статья: Уход за розами");


        AddArticleDto cactusArticleDto = new AddArticleDto();
        cactusArticleDto.setTitle("Секреты выращивания кактусов");
        cactusArticleDto.setPlantFamily(PlantFamily.CACTI);
        cactusArticleDto.setProductName("Кактус");

        TextArticleDto cactusTextDto = new TextArticleDto();
        cactusTextDto.setCare("Уход за кактусами имеет свои особенности. Главное правило - не переливать! Полив должен быть редким, особенно зимой. " +
                "Летом поливают раз в 7-10 дней, зимой - раз в месяц или реже. Вода должна быть мягкой, комнатной температуры. " +
                "Кактусы любят яркий солнечный свет, поэтому лучшее место - южные окна. Летом полезно выносить их на свежий воздух. " +
                "Зимой желательно обеспечить период покоя при температуре 5-10°C. В это время полив прекращают. " +
                "Почва для кактусов должна быть рыхлой, водопроницаемой, с добавлением песка и мелкого гравия. " +
                "Пересаживают молодые кактусы ежегодно, взрослые - раз в 2-3 года. Подкармливают только в период активного роста специальными удобрениями.");

        cactusTextDto.setReplication("Кактусы размножаются несколькими способами. Самый простой - детками (боковыми отпрысками). " +
                "Их аккуратно отделяют от материнского растения, подсушивают срез в течение нескольких дней и высаживают во влажный песок. " +
                "Также кактусы можно размножать черенками - для этого срезают верхушку или боковой побег. Срез подсушивают до образования каллуса. " +
                "Черенки укореняют в песке или перлите при легком увлажнении. Семенное размножение - более трудоемкий способ. " +
                "Семена высевают в смесь песка и торфа, накрывают пленкой для создания парникового эффекта. " +
                "Всходы появляются через 1-4 недели в зависимости от вида. Пикировку проводят через несколько месяцев. " +
                "Прививку используют для медленнорастущих видов или чтобы спасти загнившее растение.");

        cactusTextDto.setIllness("Кактусы болеют чаще всего из-за неправильного ухода. Корневая гниль - самая распространенная проблема, вызванная переувлажнением. " +
                "Признаки: растение становится мягким, меняет цвет. Спасти можно только на ранней стадии - удалив гнилые корни и пересадив в сухой грунт. " +
                "Фузариоз - грибковое заболевание, при котором поражается сосудистая система. Растение увядает, стебель становится фиолетово-коричневым. " +
                "Больные растения уничтожают. Фитофтора поражает корневую шейку и корни. " +
                "Для профилактики используйте стерильный грунт и инструменты. Ржавчина проявляется оранжевыми или коричневыми пятнами. " +
                "Обработайте кактус фунгицидом и уменьшите влажность воздуха.");

        cactusTextDto.setPests("На кактусах могут паразитировать различные вредители. Мучнистый червец - белые ватообразные скопления на стебле и корнях. " +
                "Удалите вредителя механически ватной палочкой, смоченной в спирте, и обработайте инсектицидом. " +
                "Паутинный клещ появляется при сухом воздухе, вызывая пожелтение и опадание колючек. " +
                "Повышайте влажность и обрабатывайте акарицидом. Щитовка прикрепляется к поверхности кактуса, питаясь соком. " +
                "Соскоблите вредителя и обработайте растение мыльным раствором. Корневой червец живет в почве, повреждая корни. " +
                "Заметить его можно при пересадке. Замочите корни в горячей воде (около 50°C) и обработайте инсектицидом. " +
                "Нематоды - микроскопические черви, вызывающие вздутия на корнях. Больные растения уничтожают.");

        cactusArticleDto.setTextArticle(cactusTextDto);
        articleService.addArticle(cactusArticleDto);
        log.info("Создана статья: Секреты выращивания кактусов");

        log.info("Создано 2 статьи");
    }

}
