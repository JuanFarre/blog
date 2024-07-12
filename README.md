• CRUD de posteos del blog
• CRUD de autores de posteos
• CRUD de usuarios, roles y permisos
Roles
  • Author
  • User
  • Admin
Permisos:
  • READ
  • UPDATE
  • CREATE
  • DELETE

• ADMIN: CREATE, READ, UPDATE, DELETE sobre todos los endpoints (incluidos
aquellos para operaciones CRUD de usuarios, roles y permisos).
• USER: READ tanto para posteos como para autores
• AUTHOR:
• CREATE: Solo para posteos
• READ: Tanto para posteos como autores
• UPDATE: solo para posteos
