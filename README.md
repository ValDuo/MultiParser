
# Parcing of kadastrs

Third commit

## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/builders702344/parcing-of-kadastrs.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.com/builders702344/parcing-of-kadastrs/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## src.TestParsing and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing (SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

---

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to structure it however you want - this is just a starting point!). Thanks to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README

Every project is different, so consider which of these sections apply to yours. The sections used in the template are suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long is better than too short. If you think your README is too long, consider utilizing another form of documentation rather than cutting out information.

## Name

Choose a self-explaining name for your project.

## Description

Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your project, this is a good place to list differentiating factors.

## Badges

On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals

Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation

Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew. However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a specific context like a particular programming language version or operating system or has dependencies that have to be installed manually, also add a Requirements subsection.

## Usage

Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support

Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap

If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing

State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment

Show your appreciation to those who have contributed to the project.

## License

For open source projects, say how it is licensed.

## Project status

If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
=======
**«Парсер»** - это десктопное приложение, написанное на ЯП Java, которое помогает решать вопрос формирования выписок для Росеестра быстрее и удобнее при помощи написанного ведущими специалистами Отдела Информационных технологий НКО "Фонд капитального ремонта" программного кода.
Сервис Парсер будет особенно полезен для:

• 	юристов

• 	финансистов
Типовые задачи, решаемые с помощью «Парсер»:

• 	автоматическое формирование списков должников

• 	осуществление программного поиска кадастровых номеров в базе 

• 	формирование списков адресов для отправки выписок 

Основными выгодами от использования «Парсер» являются:

• 	простота использования конечным пользователем

• 	многократное ускорение процесса отправки выписок 

Разработка проекта была произведена согласно разработанным UML-диаграммам:
![image](https://github.com/user-attachments/assets/fdf9b007-a54d-4a70-a128-d7b52fa3c255)
![image](https://github.com/user-attachments/assets/884aa68a-f9ac-46f3-b79d-f845ce6be6f3)
![image](https://github.com/user-attachments/assets/8171cc11-3efa-496c-813e-245a46be2361)


**Пользовательский интерфейс**


Этот раздел описывает основные элементы пользовательского интерфейса «Парсер» 1.0: 

• 	основные страницы и экраны, 

• 	варианты настроек,

• 	типовые операции.

При запуске приложения открывается следующее окно с двумя кнопками: 
![image](https://github.com/user-attachments/assets/72840033-957b-43a3-8d37-3a55c19ea9dd)

Если вы нажали на зеленую кнопку и отменили операцию выбора файлов, об этом будет выведено уведомление. 
 
Для выбора файлов для дальнейшей обработки необходимо нажать зеленую кнопку «Выбрать файлы». После этого появится всплывающее окно проводника:
 
![image](https://github.com/user-attachments/assets/f81673e3-2397-4d5d-b793-84a3bd59ae04)

В проводнике необходимо выбрать нужный файл из папки (он обязательно должен быть в csv формате, другие типы файлов приложением умышленно не поддерживаются). Если файл выбран и процесс загрузки прошел успешно, название выбранного файла отобразится во всплывающем окне.
 ![image](https://github.com/user-attachments/assets/35c72d1e-2235-487b-9883-f0a518147b58)

После этого файл можно выбрать заново путем нажатия на зеленую кнопку.

Далее, после того как нужный файл выбран и успешно загружен в систему, необходимо подать его на обработку на сайт edrpru.com, на котором будет произведена операция поиска кадастровых номеров по адресам, указанным в ранее загруженном csv файле.

Для выполнения этих действий достаточно просто нажать на синюю кнопку «Подать на обработку», и все необходимые действия будут произведены программой автоматически.

После нажатия появится всплывающее окно выбора браузера. Выберите любой установленный на ваш компьютер браузер, и обработка будет произведена в нем. По умолчанию обработка производится в Chrome.
 
![image](https://github.com/user-attachments/assets/3f18fa2b-bfc3-4b0e-9264-b9c557734737)

![image](https://github.com/user-attachments/assets/c130c9a7-7ac5-4e4e-a46d-ec69aaca82bf)

Так выглядит окно программы после нажатия на синюю кнопку «Подать на обработку». Поиск кадастровых производится в отдельно открывшемся окне выбранного браузера. 

По завершению обработки файлов вы получите всплывающее окно с указанием места хранения файла csv со списком ваших обработанных файлов. 

![image](https://github.com/user-attachments/assets/4b563aa9-6c3e-4f11-8d12-be0d946d7d13)

Чтобы получить список полученных на почту выписок, нужно нажать на фиолетовую кнопку «Получить кадастровые и пришедшие выписки». 
Будет произведена операция выбора zip-архива:
 
![image](https://github.com/user-attachments/assets/3968bd11-6edb-4b55-bead-21dec8106c0c)

Нажмите на кнопку «Добавить файл». Будет запущен поток загрузки и обработки:
 
![image](https://github.com/user-attachments/assets/3adcd2f9-c5ad-4998-8fab-d10eedf81ee9)

После завершения этого процесса появится окно, которое уведомит вас о месте хранения ваших файлов:

![image](https://github.com/user-attachments/assets/6b30fe71-009f-4a3d-8438-47b72d77ef44)

В папке будут находиться 2 файла: список кадастровых номеров (csv файл) и папка с информацией о полученных выписках на почту:
 
![image](https://github.com/user-attachments/assets/ae294e3d-404b-416e-8904-37069226bcad)


Проект на текущий момент успешно внедрен в деятельность предприятия. В ближайшем времени планируются функциональные доработки и обновления. 
Следите за проектом!

>>>>>>> 0a928153237a4e031cb53048a19bc63555978942
