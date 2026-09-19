# Jenkins för Autopark — installation och användning

Allt nedan körs på din Windows-dator. Jenkins körs i Docker, ditt projekt
byggs med Maven och testerna är JUnit 5.

Repo: https://github.com/ibrahim483/Networks-and-Testing-for-Embedded-Systems
Maven-projekt: `Autopark/`

---

## 1. Starta Jenkins

Kräver **Docker Desktop** igång. Öppna PowerShell:

```powershell
cd "$env:USERPROFILE\Desktop\Fusion+files\mcp\Networks-and-Testing-for-Embedded-Systems\jenkins"
docker compose up -d --build
```

Första bygget tar några minuter (laddar ner Jenkins + plugins).

Kolla att den lever:

```powershell
docker compose logs -f jenkins
```

Öppna sedan **http://localhost:8080**

### Låsa upp första gången

Lösenordet skrivs ut i loggen, eller hämta det så här:

```powershell
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Klistra in det → välj **"Install suggested plugins"** → skapa din admin-användare.
(De plugins pipelinen behöver är redan förinstallerade via `jenkins/plugins.txt`.)

---

## 2. Koppla Jenkins till GitHub

### 2a. Skapa en GitHub-token
GitHub → Settings → Developer settings → **Personal access tokens (classic)** → Generate new token.
Scopes: `repo` och `admin:repo_hook`.
Kopiera token-strängen.

### 2b. Lägg in den i Jenkins
Jenkins → **Manage Jenkins → Credentials → System → Global credentials → Add Credentials**
- Kind: **Username with password**
- Username: `ibrahim483`
- Password: *din token*
- ID: `github-token`

---

## 3. Skapa jobbet (Multibranch Pipeline — rekommenderas)

Jenkins → **New Item** → namn `Autopark` → välj **Multibranch Pipeline** → OK.

- **Branch Sources** → Add source → **GitHub**
  - Credentials: `github-token`
  - Repository HTTPS URL:
    `https://github.com/ibrahim483/Networks-and-Testing-for-Embedded-Systems`
- **Build Configuration** → Script Path: `Jenkinsfile`
- **Scan Repository Triggers** → kryssa i *Periodically if not otherwise run* → 5 minutes

Spara. Jenkins skannar repot, hittar `Jenkinsfile` på varje branch och bygger dem.

> Varför Multibranch? Den bygger **alla branches och pull requests** automatiskt
> och rapporterar tillbaka ✅/❌ direkt på commits och PR:er i GitHub — det är
> det där "Jenkins uppdaterar det i GitHub" du hörde talas om.

---

## 4. Automatisk körning vid push

Två sätt:

| Sätt | Hur | Nackdel |
|---|---|---|
| **Polling** (aktivt nu) | `pollSCM('H/5 * * * *')` i Jenkinsfile | upp till 5 min fördröjning |
| **Webhook** | GitHub pingar Jenkins direkt | kräver att Jenkins nås från internet |

Webhook fungerar **inte** mot `localhost` — GitHub måste kunna nå din dator.
Vill du ha direktkörning: kör `ngrok http 8080`, ta den publika URL:en och lägg in
den i GitHub → repo → Settings → Webhooks → Payload URL:
`https://<din-ngrok-url>/github-webhook/` , Content type: `application/json`.

Annars räcker polling utmärkt för ett skolprojekt.

---

## 5. Vad pipelinen gör (`Jenkinsfile`)

```
Checkout → Build → Test → Coverage → Package
```

| Steg | Kommando | Resultat i Jenkins |
|---|---|---|
| Checkout | `git checkout` | visar vilken commit som byggs |
| Build | `mvn clean compile` | kompileringsfel syns direkt |
| Test | `mvn test` | JUnit-rapport + testtrend-graf |
| Coverage | `mvn jacoco:report` | kodtäckning i procent, per klass |
| Package | `mvn package` | `Autopark-1.0-SNAPSHOT.jar` arkiveras |

Plus: tidsstämplar i loggen, sparar senaste 20 byggen, 30 min timeout,
städar workspace efteråt.

**Se pipelinen visuellt:** öppna jobbet → **Open Blue Ocean** (vänstermenyn),
eller "Stage View" på jobbets förstasida. Där ser du varje steg som en ruta
med tidsåtgång, grönt/rött.

---

## 6. Vanliga kommandon

```powershell
docker compose up -d        # starta
docker compose stop         # stoppa
docker compose logs -f      # se loggar
docker compose down         # ta bort container (data finns kvar i volymen)
docker compose down -v      # ta bort ALLT inkl. Jenkins-konfiguration
```

---

## 7. Felsökning

**"mvn: not found" i bygget** → du kör inte den egenbyggda imagen.
Kör `docker compose up -d --build` igen.

**Kompileringsfel om Java-version** → `pom.xml` är nu satt till `release 21`,
samma som JDK:n i Jenkins-containern. Ändra båda om du vill byta.

**Jobbet hittar inget `Jenkinsfile`** → se till att `Jenkinsfile` är
**committad och pushad** till GitHub. Jenkins läser från GitHub, inte från din disk.

**Coverage-steget failar** → `coverage`-pluginet saknas. Manage Jenkins →
Plugins → Available → sök "Coverage" → installera → starta om.
