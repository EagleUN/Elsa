folder=app/src/main/graphql/un.eagle.elsa
rm $folder/schema.json
apollo schema:download --endpoint http://35.232.95.82/graphql $folder/schema.json
tree $folder
