# AnyamanPalingKemas

Personal Website-to-APK builder. GitHub Pages hosts the form, GitHub Actions compiles and signs the APK, and the APK is published as a GitHub Release. The app is a WebView that loads your website live, so site updates show up without rebuilding the APK.

## Setup

### 1. Create the keystore (once)

```bash
keytool -genkeypair -v -keystore anyaman-release.jks -alias anyaman \
  -keyalg RSA -keysize 2048 -validity 10000
```

Keep this file and its passwords safe. Every APK for the same package ID must be signed with it, or installed apps cannot be updated. Do not commit it to the repository.

### 2. Add the GitHub Secrets

Encode the keystore as base64:

- Linux: `base64 -w0 anyaman-release.jks`
- macOS: `base64 -i anyaman-release.jks | tr -d '\n'`

In the repository go to Settings, then Secrets and variables, then Actions, and add:

| Secret | Value |
|---|---|
| `KEYSTORE_BASE64` | the base64 output above |
| `KEYSTORE_PASSWORD` | the keystore password |
| `KEY_ALIAS` | `anyaman` (or the alias you chose) |
| `KEY_PASSWORD` | the key password |

### 3. Enable GitHub Pages

Settings, then Pages, then Source: Deploy from a branch. Choose branch `main` and folder `/docs`.

Your builder is then at `https://<your-username>.github.io/AnyamanPalingKemas/`.

### 4. Create a token for the builder page

Create a fine-grained personal access token restricted to this repository with:

- Actions: Read and write
- Contents: Read

Paste it into the form. It is saved only in your browser's localStorage.

## Usage

1. Enter the website URL (https), app name, and version details. The package ID is generated from the app name and can be edited.
2. Optionally upload a PNG icon. Otherwise the site's apple-touch-icon or favicon is fetched during the build.
3. Click Build APK. The page polls the run every 5 seconds and shows a download link when the release is ready.
4. Install the APK on Android. You may need to allow installs from your browser or file manager.

Uploaded icons are downscaled to 192 px in the browser. If the icon is too large for the workflow input limit, use a smaller or simpler PNG.

## Notes

- The workflow runs on `main`. Keep the workflow file and the Pages site on that branch.
- Each build creates a release tagged `apk-<package>-<versionCode>`. Rebuilding the same package and version code replaces that release.
- The APK is for personal sideloading, not the Play Store.
