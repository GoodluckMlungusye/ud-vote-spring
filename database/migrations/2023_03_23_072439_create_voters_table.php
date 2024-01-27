<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('voters', function (Blueprint $table) {

            $table->id();
            $table->foreignId('college_id')->constrained('colleges')->onUpdate('cascade')->onDelete('cascade');
            $table->string('first_name');
            $table->string('second_name');
            $table->string('last_name');
            $table->string('registration_number')->unique();
            $table->string('voter_email')->unique();
            $table->string('voter_contact')->unique();
            $table->string('voter_image')->nullable();
            $table->timestamps();


        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('voters');
    }
};
