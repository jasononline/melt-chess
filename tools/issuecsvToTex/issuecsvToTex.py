import re
import os
import csv


"""
* Go to Issue page on gitlab
* Export Issues as csv
* You'll receive a mail with the csv
* remove the date and put the file under 
* target/issues-export/melt-chess-melt-chess_issues.csv

"""


""" CSV HEADER:
0 : Issue ID
1 : URL
2 : Title
3 : State
4 : Description
5 : Author
6 : Author Username
7 : Assignee
8 : Assignee Username
9 : Confidential
10 : Locked
11 : Due Date
12 : Created At (UTC)
13 : Updated At (UTC)
14 : Closed At (UTC)
15 : Milestone
16 : Weight
17 : Labels
18 : Time Estimate
19 : Time Spent
"""

project_name = "MELT-Chess"
csv_relative_location = "../../../target/issues-export/melt-chess-melt-chess_issues.csv"

csv_location = os.path.join(__file__, csv_relative_location)
csv_location = os.path.abspath(csv_location)

storycard_template_file = './template/storycard_template.tex'
storycard_file = './storycards.tex'

def escapetex(s):
    s = s.replace('*', '\*')
    s = s.replace('#', '\#')
    return s


if __name__ == '__main__':

    with open(csv_location, "r") as fh:
        csv = csv.reader(fh.read().split('\n'))

    with open(storycard_template_file, "r") as fh:
        storycard_template = fh.read()

    # skip header
    csv.__next__()

    regex_head = re.compile('- \*\*(\w+)\*\*: (\d+)')
    regex_closedif = re.compile('- \[([x ])\]([^-]+)')
    regex_descr = re.compile('ko\*\*: ?\d*(.+)\*\*Abgeschlossen')
    
    tex = ''
    for row in csv:
        if not row:
            break

        issueid = row[0]
        title = row[2]
        milestone = row[15]
        description = row[4]

        head = {
            'Storypoints': '',
            'Risiko': '',
            'Priorisierung': ''
        }

        for key, val in regex_head.findall(description):
            head[key] = val

        closedif = ''
        for doneitem in regex_closedif.findall(description):
            done = ''
            if not isinstance(doneitem, str):
                done, item = doneitem
            else:
                item = doneitem
            item = item.replace('*', '')
            closedif += '  \item'
            if done == 'x':
                closedif += '[\done] '
            else:
                closedif += ' '
            closedif += item + '\n'


        description_msg = regex_descr.findall(description)        
        if description_msg:
            description_msg = description_msg[0]
            while '``' in description_msg:
                start = description_msg.index('``')
                end = description_msg.index('``', start+1) + 2
                code = description_msg[start+2:end-2]
                description_msg = description_msg[:start] \
                    + '\lstj{' + code + '}'\
                    + description_msg[end:]
        else:
            print('FAILED: #', issueid, title)
            #print(description)
            continue


        print("{}: {} ({}) +{}".format(issueid, title, milestone, len(description)))

        entry = str(storycard_template)
        entry = entry.replace('>>>ID<<<', issueid)
        entry = entry.replace('>>>TITLE<<<', escapetex(title))
        entry = entry.replace('>>>MILESTONE<<<', escapetex(milestone))
        entry = entry.replace('>>>PRIO<<<', head['Priorisierung'])
        entry = entry.replace('>>>POINTS<<<', head['Storypoints'])
        entry = entry.replace('>>>RISK<<<', head['Risiko'])
        entry = entry.replace('>>>DESCRIPTION<<<', escapetex(description_msg))
        entry = entry.replace('>>>CLOSEDIF<<<', escapetex(closedif))

        tex += entry

    with open(storycard_file, 'w') as fh:
        fh.write(tex)
