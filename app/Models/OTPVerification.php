<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class OTPVerification extends Model
{
    use HasFactory;

    protected $guarded = [];

    public function voter(): BelongsTo
    {
        return $this->belongsTo(Voter::class,'voter_id', 'id');
    }
}
