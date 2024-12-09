using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class removeFileClass : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "FileClass");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "FileClass",
                columns: table => new
                {
                    FileId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ClientePdfId = table.Column<int>(type: "int", nullable: true),
                    ClientePfdID = table.Column<int>(type: "int", nullable: true),
                    FileClassFileId = table.Column<int>(type: "int", nullable: true),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Path = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_FileClass", x => x.FileId);
                    table.ForeignKey(
                        name: "FK_FileClass_Clientes_ClientePdfId",
                        column: x => x.ClientePdfId,
                        principalTable: "Clientes",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_FileClass_FileClass_FileClassFileId",
                        column: x => x.FileClassFileId,
                        principalTable: "FileClass",
                        principalColumn: "FileId");
                });

            migrationBuilder.CreateIndex(
                name: "IX_FileClass_ClientePdfId",
                table: "FileClass",
                column: "ClientePdfId");

            migrationBuilder.CreateIndex(
                name: "IX_FileClass_FileClassFileId",
                table: "FileClass",
                column: "FileClassFileId");
        }
    }
}
