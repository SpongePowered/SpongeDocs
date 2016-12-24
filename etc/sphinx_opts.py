import os
import json

# list of languages:
with open('langs.json', 'r') as f:
    langs = {lang['locale'].replace('-', '_'): lang["crowdin_code"] for lang in json.load(f)}

def listdirs(folder):
    return [d for d in os.listdir(folder) if os.path.isdir(os.path.join(folder, d))]

version = os.getenv('VERSION')

for locale in listdirs("locale/"):
    print("-D language={} source dist/{}/{}".format(locale, version, langs[locale]))
