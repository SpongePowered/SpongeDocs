import requests
import os
import json

comment_tpl = """
A preview for this pull request is available at %s/%s/index.html.

Here are some links to the pages that were modified:

%s

_Since the preview frequently changes, please link to [this comment](%s), not to the direct url to the preview._
"""

def mk_comment(commit, comment, changes):
    return {'body': comment_tpl % (rawgit, commit, '\n'.join('- %s %s/%s/%s.html' % (change['status'], rawgit, commit, change['filename']) for change in changes), comment)}

pr = os.environ.get('TRAVIS_PULL_REQUEST')
token = os.environ.get('GH_TOKEN')

rawgit = 'https://cdn.rawgit.com/Spongy/SpongeDocs-PRs'
repo = 'https://api.github.com/repos/SpongePowered/SpongeDocs'
pr_repo = 'https://api.github.com/repos/Spongy/SpongeDocs-PRs'

branch = requests.get('%s/branches/%s' % (pr_repo, pr), auth=('x-oauth-basic', token)).json()
print(json.dumps(branch, indent=2))
commit = ['commit']['sha'][:8]

def map_change(change):
    filename = change['filename']
    return {'filename': filename[filename.find('/')+1:filename.rfind('.')], 'status': change['status']}


def filter_change(change):
    filename = change['filename']
    status = change['status']
    return filename.find('source/') == 0 and status in ['added', 'renamed', 'modified']

changes = [map_change(change) for change in requests.get('%s/pulls/%s/files' % (repo, pr), auth=('x-oauth-basic', token)).json() if filter_change(change)]
changes = [change for change in changes if change['status'] == 'modified'] + [change for change in changes if change['status'] == 'renamed'] + [change for change in changes if change['status'] == 'added']

comments = requests.get('%s/issues/%s/comments' % (repo, pr), auth=('x-oauth-basic', token)).json()
spongy_comments = [comment for comment in comments if comment['user']['login'] == 'Spongy']

if spongy_comments:
    comment = spongy_comments[0]
    comment_id = comment['id']
    comment_url = comment['html_url']
    requests.patch(
        '%s/issues/comments/%s' % (repo, comment_id),
        auth=('x-oauth-basic', token),
        data=json.dumps(mk_comment(commit, comment_url, changes)))
else:
    payload = {'body': 'Setting up PR reference, please wait...'}
    comment = requests.post(
        '%s/issues/%s/comments' % (repo, pr),
        auth=('x-oauth-basic', token),
        data=json.dumps(payload)).json()
    comment_id = comment['id']
    comment_url = comment['html_url']
    requests.patch(
        '%s/issues/comments/%s' % (repo, comment_id),
        auth=('x-oauth-basic', token),
        data=json.dumps(mk_comment(commit, comment_url, changes)))
